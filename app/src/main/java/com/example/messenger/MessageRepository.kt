package com.example.messenger

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MessageRepository(
    private val context: Context
) {
    companion object {
        private const val TAG = "MessageRepository"
    }

    private val apiService = RetrofitClient.apiService
    private val database = MessengerDatabase.getDatabase(context)
    private val messageDao = database.messageDao()

    fun getMessages(): Flow<List<MessageEntity>> {
        return messageDao.getAllMessages()
    }

    suspend fun refreshMessages() {
        Log.d(TAG, "Начинаем обновление сообщений")
        try {
            if (isNetworkAvailable()) {
                Log.d(TAG, "Сеть доступна, загружаем с API")
                val response = withContext(Dispatchers.IO) {
                    apiService.getPosts()
                }

                if (response.isSuccessful) {
                    val messages = response.body() ?: emptyList()
                    Log.d(TAG, "Получено ${messages.size} сообщений с API")

                    val messageEntities = messages.map { message ->
                        MessageEntity(
                            id = message.id,
                            userId = message.userId,
                            title = message.title,
                            body = message.body,
                        )
                    }

                    withContext(Dispatchers.IO) {
                        messageDao.deleteAllMessages()
                        messageDao.insertAll(messageEntities)
                    }

                    Log.d(TAG, "Сообщения сохранены в базу данных")
                } else {
                    Log.e(TAG, "Ошибка API: ${response.code()} - ${response.message()}")
                }
            } else {
                Log.d(TAG, "Сеть недоступна, используем локальную базу")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Ошибка при обновлении сообщений: ${e.message}", e)
        }
    }

    suspend fun getMessageCount(): Int {
        return withContext(Dispatchers.IO) {
            messageDao.getMessageCount()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}