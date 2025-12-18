package com.example.oktimessenger

import android.content.Context
import android.util.Log

class MessageRepository(context: Context) {

    private val dao = AppDatabase.getInstance(context).messageDao()
    private val api = RetrofitInstance.api

    suspend fun getMessages(): List<MessageEntity> {
        return try {
            val response = api.getMessages()

            val entities = response.map {
                MessageEntity(it.id, it.title, it.body)
            }

            dao.clearMessages()
            dao.insertMessages(entities)

            Log.d("Repository", "Loaded from API")
            entities
        } catch (e: Exception) {
            Log.d("Repository", "Loaded from DB")
            dao.getAllMessages()
        }
    }
}
