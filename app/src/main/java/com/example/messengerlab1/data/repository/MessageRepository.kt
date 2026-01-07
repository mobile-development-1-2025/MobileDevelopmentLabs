package com.example.messengerlab1.data.repository

import com.example.messengerlab1.data.db.MessageDao
import com.example.messengerlab1.data.db.MessageEntity
import com.example.messengerlab1.data.network.RetrofitClient

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
            val likedMap = dao.getLikedMap().associate { it.id to it.liked }

            val remote = RetrofitClient.api.getMessages()
            val entities = remote.map {
                MessageEntity(
                    id = it.id,
                    title = it.title,
                    body = it.body,
                    liked = likedMap[it.id] ?: false
                )
            }

            dao.insertAll(entities)
            dao.getAll()
        } catch (e: Exception) {
            dao.getAll()
        }
    }

    suspend fun setLiked(id: Int, liked: Boolean) {
        dao.setLiked(id, liked)
    }
}