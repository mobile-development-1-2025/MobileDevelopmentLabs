package com.example.messengerlab.data.repository

import retrofit2.Response
import android.util.Log
import com.example.messengerlab.data.api.MessengerApi
import com.example.messengerlab.data.db.MessageDao
import com.example.messengerlab.data.model.MessageEntity

class MessageRepository(
    private val api: MessengerApi,
    private val dao: MessageDao
) {
    suspend fun refreshMessages() {
        try {
            val response = api.getMessages()
            if (response.isSuccessful) {
                val messages = response.body()?.posts ?: emptyList()
                Log.d("REPO_DEBUG", "Успешно загружено: ${messages.size}")

                dao.clearAll()
                dao.insertAll(messages)
            }
        } catch (e: Exception) {
            Log.e("REPO_DEBUG", "Ошибка загрузки: ${e.message}")
        }
    }

    suspend fun getAllMessages(): List<MessageEntity> = dao.getAllMessages()
}