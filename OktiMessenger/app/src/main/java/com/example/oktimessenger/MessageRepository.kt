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
                MessageEntity(
                    id = it.id,
                    title = it.title,
                    body = it.body
                )
            }
            dao.clearMessages()
            dao.insertMessages(entities)
            entities

        } catch (e: Exception) {
            val cached = dao.getAllMessages()
            cached
        }
    }

    suspend fun toggleLike(message: MessageEntity): MessageEntity {
        val updated = message.copy(liked = !message.liked)
        dao.updateMessage(updated)
        return updated
    }
}
