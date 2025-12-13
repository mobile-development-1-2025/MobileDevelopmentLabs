package com.example.vsemk.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.example.vsemk.data.local.AppDatabase
import com.example.vsemk.data.local.MessageEntity
import com.example.vsemk.data.remote.MessageApiService
import com.example.vsemk.data.remote.MessageResponse
import com.example.vsemk.data.remote.RetrofitClient
import kotlinx.coroutines.flow.Flow

class MessageRepository(
    private val context: Context
) {
    
    companion object {
        private const val TAG = "MessageRepository"
    }
    
    private val database = AppDatabase.getDatabase(context)
    private val messageDao = database.messageDao()
    private val apiService: MessageApiService = RetrofitClient.messageApiService
    
    suspend fun loadMessages() {
        try {
            if (isNetworkAvailable()) {
                Log.d(TAG, "Сеть доступна, загружаем данные из API")
                val messages = fetchMessagesFromApi()
                messageDao.insertMessages(messages)
            } else {
                Log.d(TAG, "Сеть недоступна, используем данные из БД")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Ошибка при получении сообщений", e)
        }
    }
    
    suspend fun refreshMessages() {
        try {
            Log.d(TAG, "Обновление сообщений из API")
            val messages = fetchMessagesFromApi()
            messageDao.deleteAllMessages()
            messageDao.insertMessages(messages)
        } catch (e: Exception) {
            Log.e(TAG, "Ошибка при обновлении сообщений", e)
            throw e
        }
    }
    
    fun getAllMessages(): Flow<List<MessageEntity>> {
        return messageDao.getAllMessages()
    }
    
    private suspend fun fetchMessagesFromApi(): List<MessageEntity> {
        val response = apiService.getMessages()
        return response.map { it.toEntity() }
    }
    
    private fun MessageResponse.toEntity(): MessageEntity {
        return MessageEntity(
            id = this.id,
            title = this.title,
            body = this.body,
            userId = this.userId
        )
    }
    
    private fun isNetworkAvailable(): Boolean {
        return try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            if (connectivityManager == null) {
                Log.w(TAG, "ConnectivityManager недоступен")
                return false
            }
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork ?: return false
                val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            } else {
                @Suppress("DEPRECATION")
                val allNetworks = connectivityManager.allNetworks
                for (net in allNetworks) {
                    val networkInfo = connectivityManager.getNetworkInfo(net)
                    if (networkInfo != null && networkInfo.state == android.net.NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
                false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Ошибка при проверке сети", e)
            false
        }
    }
}

