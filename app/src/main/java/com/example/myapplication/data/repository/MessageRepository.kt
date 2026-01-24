package com.example.myapplication.data.repository

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.annotation.RequiresPermission
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.local.entity.Message
import com.example.myapplication.data.remote.RetrofitClient
import kotlinx.coroutines.flow.Flow

class MessageRepository(private val context: Context) {

    private val messageDao = AppDatabase.getDatabase(context).messageDao()
    private val apiService = RetrofitClient.apiService

    val messages: Flow<List<Message>> = messageDao.getAllMessages()

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    suspend fun fetchMessages(forceRefresh: Boolean = false): NetworkResult<List<Message>> {
        return if (isNetworkAvailable()) {
            fetchMessagesFromNetwork()
        } else {
            Log.w("Repository", "Нет интернет-соединения, загружаем из базы")
            val cachedMessages = getCachedMessages()
            if (cachedMessages.isNotEmpty()) {
                NetworkResult.Success(cachedMessages)
            } else {
                NetworkResult.Error("Нет интернет-соединения и нет кэшированных данных")
            }
        }
    }

    private suspend fun fetchMessagesFromNetwork(): NetworkResult<List<Message>> {
        return try {
            Log.i("Repository", "Загружаем сообщения из сети...")
            val response = apiService.getAllMessages()
            val messages = response.map { it.toEntity() }
            messageDao.deleteAllMessages()
            messageDao.insertMessages(messages)

            Log.i("Repository", "Загружено ${messages.size} сообщений")
            NetworkResult.Success(messages)
        } catch (e: Exception) {
            Log.e("Repository", "Ошибка загрузки из сети: ${e.message}")
            val cachedMessages = getCachedMessages()
            if (cachedMessages.isNotEmpty()) {
                NetworkResult.Success(cachedMessages)
            } else {
                NetworkResult.Error(
                    message = "Ошибка загрузки: ${e.message}",
                    exception = e
                )
            }
        }
    }

    private suspend fun getCachedMessages(): List<Message> {
        return try {
            val count = messageDao.getMessagesCount()
            Log.i("Repository", "Кэшировано сообщений: $count")
            var result = listOf<Message>()
            messages.collect { result = it }
            result
        } catch (e: Exception) {
            Log.e("Repository", "Ошибка чтения из базы: ${e.message}")
            emptyList()
        }
    }
}