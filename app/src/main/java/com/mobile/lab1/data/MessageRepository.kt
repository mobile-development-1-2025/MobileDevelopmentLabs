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
        val dtos = api.getMessages()
        val entities = dtos.map { it.toEntity() }

        dao.clearMessages()
        dao.insertMessages(entities)

        return entities.map { it.toDomain() }
    }

    private suspend fun loadFromDb(): List<Message> {
        val entities = dao.getAllMessages()
        return entities.map { it.toDomain() }
    }
}