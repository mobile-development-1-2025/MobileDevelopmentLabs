package com.example.messengerapp.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.messengerapp.data.local.AppDatabase
import com.example.messengerapp.data.local.MessageEntity
import com.example.messengerapp.data.remote.RetrofitClient

class MessageRepository(private val context: Context) {

    private val TAG = "MessageRepository"
    private val dao = AppDatabase.getInstance(context).messageDao()
    private val api = RetrofitClient.api

    suspend fun getMessages(forceRefresh: Boolean = false): List<MessageEntity> {
        if (forceRefresh || isNetworkAvailable()) {
            try {
                Log.d(TAG, "Загрузка из сети...")
                val dtos = api.getMessages()
                val entities = dtos.map {
                    MessageEntity(id = it.id, userId = it.userId, title = it.title, body = it.body)
                }
                dao.clearAll()
                dao.insertAll(entities)
                Log.d(TAG, "Сохранено в базу: ${entities.size} сообщений")
                return entities
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка сети, загружаю из базы: ${e.message}")
            }
        } else {
            Log.d(TAG, "Нет сети, загружаю из базы...")
        }
        return dao.getAllMessages()
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
