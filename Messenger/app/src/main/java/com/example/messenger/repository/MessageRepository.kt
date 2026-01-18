package com.example.messenger.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.messenger.api.RetrofitClient
import com.example.messenger.data.Message
import com.example.messenger.data.MessageDatabase
import com.example.messenger.data.MessageDao
import com.example.messenger.data.MessageResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageRepository(private val context: Context) {

    companion object {
        private const val TAG = "MessageRepository"
    }

    private val messageDao: MessageDao = MessageDatabase.getDatabase(context).messageDao()
    private val apiService = RetrofitClient.apiService

    suspend fun getMessages(): Result<List<Message>> {
        return withContext(Dispatchers.IO) {
            try {
                if (isNetworkAvailable()) {
                    Log.d(TAG, "Loading messages from API")
                    val response = apiService.getMessages()
                    val russianMessages = getRussianMessages()
                    val userNames = listOf("Анна", "Иван", "Мария")
                    val messages = response.take(3).mapIndexed { index, messageResponse ->
                        val russianMessage = russianMessages.getOrElse(index) { russianMessages.last() }
                        Message(
                            id = messageResponse.id,
                            userId = messageResponse.userId,
                            userName = userNames.getOrElse(index) { "Пользователь ${messageResponse.userId}" },
                            title = russianMessage.title,
                            body = russianMessage.body,
                            isLiked = false
                        )
                    }
                    messageDao.insertMessages(messages)
                    Log.d(TAG, "Messages saved to database: ${messages.size}")
                    Result.success(messages)
                } else {
                    Log.d(TAG, "Network unavailable, loading from database")
                    val currentMessages = messageDao.getAllMessagesSync()
                    if (currentMessages.isEmpty()) {
                        Result.failure(Exception("No network connection and no cached data"))
                    } else {
                        Result.success(currentMessages)
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading messages", e)
                val cachedMessages = messageDao.getAllMessagesSync()
                if (cachedMessages.isNotEmpty()) {
                    Log.d(TAG, "Using cached messages: ${cachedMessages.size}")
                    Result.success(cachedMessages)
                } else {
                    Result.failure(e)
                }
            }
        }
    }

    suspend fun refreshMessages(): Result<List<Message>> {
        return withContext(Dispatchers.IO) {
            try {
                if (isNetworkAvailable()) {
                    Log.d(TAG, "Refreshing messages from API")
                    val response = apiService.getMessages()
                    val russianMessages = getRussianMessages()
                    val userNames = listOf("Анна", "Иван", "Мария")
                    val messages = response.take(3).mapIndexed { index, messageResponse ->
                        val russianMessage = russianMessages.getOrElse(index) { russianMessages.last() }
                        Message(
                            id = messageResponse.id,
                            userId = messageResponse.userId,
                            userName = userNames.getOrElse(index) { "Пользователь ${messageResponse.userId}" },
                            title = russianMessage.title,
                            body = russianMessage.body,
                            isLiked = false
                        )
                    }
                    messageDao.deleteAllMessages()
                    messageDao.insertMessages(messages)
                    Log.d(TAG, "Messages refreshed: ${messages.size}")
                    Result.success(messages)
                } else {
                    Log.d(TAG, "Network unavailable for refresh")
                    Result.failure(Exception("No network connection"))
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error refreshing messages", e)
                Result.failure(e)
            }
        }
    }

    fun getAllMessages() = messageDao.getAllMessages()

    suspend fun toggleLike(messageId: Int, isLiked: Boolean) {
        withContext(Dispatchers.IO) {
            messageDao.updateLikeStatus(messageId, isLiked)
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    private fun getRussianMessages(): List<MessageResponse> {
        return listOf(
            MessageResponse(1, 1, "Добро пожаловать в мессенджер!", "Это первое сообщение в вашей ленте. Здесь вы можете общаться с друзьями и делиться новостями."),
            MessageResponse(2, 1, "Новые возможности приложения", "Мы добавили поддержку темной темы и улучшили производительность. Наслаждайтесь обновленным интерфейсом!"),
            MessageResponse(3, 2, "Советы по использованию", "Не забывайте обновлять список сообщений, нажав кнопку 'Обновить'. Все данные сохраняются локально для офлайн-доступа.")
        )
    }
}
