package com.example.messengerapp.data.repository

import com.example.messengerapp.data.local.MessageDao
import com.example.messengerapp.data.model.MessageEntity
import com.example.messengerapp.data.remote.ApiService

class MessageRepository (
    private val api: ApiService,
    private val dao: MessageDao
) {
    suspend fun getMessages(): List<MessageEntity> {
        return try {
            val messages = api.getMessages()

            dao.clear()
            dao.insertAll(messages)

            messages
        } catch (e: Exception) {
            dao.getAll()
        }
    }
}
