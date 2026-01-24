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
            val cached = dao.getAll()
            val likedById = cached.associate { it.id to it.isLiked }

            val remote = api.getMessages()
            val entities = remote.map { dto ->
                MessageEntity(
                    id = dto.id,
                    title = dto.name,
                    author = dto.email,
                    text = dto.body,
                    isLiked = likedById[dto.id] ?: false
                )
            }

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

    suspend fun toggleLike(id: Int) = dao.toggleLike(id)
}
