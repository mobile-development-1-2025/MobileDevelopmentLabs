package com.example.messengerapp.data

import com.example.messengerapp.data.local.MessageDao
import com.example.messengerapp.data.local.MessageEntity
import com.example.messengerapp.data.remote.ApiService
import java.io.IOException

class MessageRepository(
    private val api: ApiService,
    private val dao: MessageDao
) {
    suspend fun loadMessages(): List<MessageEntity> {
        return try {
            val remote = api.getMessages()
            val entities = remote.map { it.toEntity() }
            dao.insertAll(entities)
            entities
        } catch (e: IOException) {
            // нет сети
            dao.getAll()
        } catch (e: Exception) {
            // сломался парсинг
            dao.getAll()
        }
    }

    suspend fun refresh(): List<MessageEntity> {
        return loadMessages()
    }
}
