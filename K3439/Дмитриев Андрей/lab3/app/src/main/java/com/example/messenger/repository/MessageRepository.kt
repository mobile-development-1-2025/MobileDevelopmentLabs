package com.example.messenger.repository

import android.util.Log
import com.example.messenger.api.RetrofitClient
import com.example.messenger.data.Message
import com.example.messenger.data.MessageDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageRepository(private val messageDao: MessageDao) {
    private val TAG = "MessageRepository"

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

    private suspend fun fetchFromApi(): Result<List<Message>> {
        return try {
            val messages = RetrofitClient.messageApi.getMessages()
            Log.d(TAG, "Загружено ${messages.size} сообщений из API")
            messageDao.deleteAllMessages()
            messageDao.insertMessages(messages)
            Log.d(TAG, "Сообщения сохранены в локальную БД")
            Result.success(messages)
        } catch (e: Exception) {
            Log.e(TAG, "Ошибка загрузки из API: ${e.message}")
            Result.failure(e)
        }
    }
}
