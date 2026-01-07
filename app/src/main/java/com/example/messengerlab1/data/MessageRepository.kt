package com.example.messengerlab1.data

import com.example.messengerlab1.data.api.RetrofitClient
import com.example.messengerlab1.data.db.MessageDao
import com.example.messengerlab1.data.db.MessageEntity

class MessageRepository(
    private val dao: MessageDao
) {
    suspend fun getMessages(forceRefresh: Boolean): List<MessageEntity> {
        if (forceRefresh) {
            return refreshFromNetworkOrFallback()
        }

        val cached = dao.getAll()
        return if (cached.isNotEmpty()) cached else refreshFromNetworkOrFallback()
    }

    suspend fun refreshFromNetworkOrFallback(): List<MessageEntity> {
        return try {
            val remote = RetrofitClient.api.getMessages()
            val entities = remote.map { MessageEntity(it.id, it.title, it.body) }
            dao.clear()
            dao.insertAll(entities)
            dao.getAll()
        } catch (e: Exception) {
            dao.getAll()
        }
    }
}