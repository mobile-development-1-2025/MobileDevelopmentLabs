package com.example.messenger.repository

import android.util.Log
import com.example.messenger.api.RetrofitClient
import com.example.messenger.data.Message
import com.example.messenger.data.MessageDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Репозиторий для работы с сообщениями
 * Реализует паттерн Repository для абстракции источников данных
 */
class MessageRepository(private val messageDao: MessageDao) {
    private val TAG = "MessageRepository"

    /**
     * Получение потока сообщений для реактивного обновления UI
     */
    val messagesFlow: Flow<List<Message>> = messageDao.getAllMessagesFlow()

    /**
     * Загрузка сообщений с возможностью принудительного обновления
     */
    suspend fun getMessages(forceRefresh: Boolean = false): Result<List<Message>> {
        return withContext(Dispatchers.IO) {
            try {
                if (forceRefresh) {
                    Log.d(TAG, "Принудительное обновление данных из API")
                    fetchFromApi()
                } else {
                    val cachedMessages = messageDao.getAllMessages()
                    if (cachedMessages.isNotEmpty()) {
                        Log.d(TAG, "Загружено ${cachedMessages.size} сообщений из локальной БД")
                        Result.success(cachedMessages)
                    } else {
                        Log.d(TAG, "Локальная БД пуста, загрузка из API")
                        fetchFromApi()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка при загрузке данных из API: ${e.message}")
                val cachedMessages = messageDao.getAllMessages()
                if (cachedMessages.isNotEmpty()) {
                    Log.d(TAG, "Используем кэшированные данные: ${cachedMessages.size} сообщений")
                    Result.success(cachedMessages)
                } else {
                    Log.e(TAG, "Нет кэшированных данных")
                    Result.failure(e)
                }
            }
        }
    }

    /**
     * Загрузка данных из API и сохранение в БД
     */
    private suspend fun fetchFromApi(): Result<List<Message>> {
        return try {
            val responses = RetrofitClient.messageApi.getMessages()
            Log.d(TAG, "Загружено ${responses.size} сообщений из API")
            
            // Получаем существующие сообщения для сохранения состояния лайков
            val existingMessages = messageDao.getAllMessages()
            val likedMessageIds = existingMessages.filter { it.isLiked }.map { it.id }.toSet()
            
            // Конвертируем ответы в сообщения, сохраняя лайки
            val messages = responses.map { response ->
                response.toMessage().copy(isLiked = response.id in likedMessageIds)
            }
            
            messageDao.deleteAllMessages()
            messageDao.insertMessages(messages)
            Log.d(TAG, "Сообщения сохранены в локальную БД")
            Result.success(messages)
        } catch (e: Exception) {
            Log.e(TAG, "Ошибка загрузки из API: ${e.message}")
            Result.failure(e)
        }
    }

    /**
     * Синхронизация данных для WorkManager
     * Возвращает true при успехе
     */
    suspend fun syncMessages(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val result = fetchFromApi()
                result.isSuccess
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка синхронизации: ${e.message}")
                false
            }
        }
    }

    /**
     * Переключение состояния лайка для сообщения
     */
    suspend fun toggleLike(messageId: Int) {
        withContext(Dispatchers.IO) {
            val messages = messageDao.getAllMessages()
            val message = messages.find { it.id == messageId }
            message?.let {
                val newLikeStatus = !it.isLiked
                messageDao.updateLikeStatus(messageId, newLikeStatus)
                Log.d(TAG, "Лайк для сообщения $messageId: $newLikeStatus")
            }
        }
    }
}
