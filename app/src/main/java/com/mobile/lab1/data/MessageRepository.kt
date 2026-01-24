package com.mobile.lab1.data

import android.util.Log

class MessageRepository(
    private val api: MessageApi,
    private val dao: MessageDao
) {

    companion object {
        private const val TAG = "MessageRepository"
    }

    suspend fun loadMessages(forceRefresh: Boolean): List<Message> {
        return if (forceRefresh) {
            tryNetworkThenDb()
        } else {
            try {
                tryNetworkThenDb()
            } catch (e: Exception) {
                Log.e(TAG, "Network failed, loading from DB", e)
                loadFromDb()
            }
        }
    }

    private suspend fun tryNetworkThenDb(): List<Message> {
        val fromDb = dao.getAllMessages()
        val likedMap = fromDb.associateBy({ it.id }, { it.isLiked })

        val dtos = api.getMessages()
        val entities = dtos.map { dto ->
            val oldLiked = likedMap[dto.id] ?: false
            dto.toEntity(oldLiked)
        }

        dao.clearMessages()
        dao.insertMessages(entities)

        return entities.map { it.toDomain() }
    }

    private suspend fun loadFromDb(): List<Message> =
        dao.getAllMessages().map { it.toDomain() }

    suspend fun toggleLike(message: Message) {
        dao.updateLike(message.id, !message.isLiked)
    }
}