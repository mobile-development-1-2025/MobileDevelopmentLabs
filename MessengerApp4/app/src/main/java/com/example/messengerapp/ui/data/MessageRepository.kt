package com.example.messenger.data

import android.util.Log
import kotlinx.coroutines.flow.Flow

class MessageRepository(private val api: MessageApiService, private val db: AppDatabase) {
    private val dao = db.messageDao()

    fun getMessagesFlow(): Flow<List<Message>> {
        return dao.getAllMessagesFlow()
    }

    suspend fun fetchMessages(limit: Int): List<Message> {
        return try {
            Log.d("MessageRepository", "Загрузка из API: limit=$limit")

            val response = api.getMessages(limit, 0)
            Log.d("MessageRepository", "Получено ${response.posts.size} сообщений из API")

            val existingMessages = dao.getAllMessages().associateBy { it.id }

            val updatedMessages = response.posts.map { newMsg ->
                val existing = existingMessages[newMsg.id]
                if (existing != null) {
                    newMsg.copy(
                        isLiked = existing.isLiked,
                        isDisliked = existing.isDisliked,
                        userLikes = existing.userLikes,
                        userDislikes = existing.userDislikes
                    )
                } else {
                    newMsg
                }
            }

            dao.deleteAll()
            dao.insertMessages(updatedMessages)

            updatedMessages
        } catch (e: Exception) {
            Log.e("MessageRepository", "Ошибка загрузки из API, используем кэш", e)

            val cachedMessages = dao.getAllMessages()
            Log.d("MessageRepository", "Загружено ${cachedMessages.size} сообщений из БД")

            cachedMessages
        }
    }

    suspend fun toggleLike(message: Message) {
        val newIsLiked = !message.isLiked
        val newUserLikes = if (newIsLiked) message.userLikes + 1 else message.userLikes - 1

        val updatedMessage = message.copy(
            isLiked = newIsLiked,
            isDisliked = false,
            userLikes = newUserLikes,
            userDislikes = if (message.isDisliked) message.userDislikes - 1 else message.userDislikes
        )
        dao.updateMessage(updatedMessage)
    }

    suspend fun toggleDislike(message: Message) {
        val newIsDisliked = !message.isDisliked
        val newUserDislikes = if (newIsDisliked) message.userDislikes + 1 else message.userDislikes - 1

        val updatedMessage = message.copy(
            isDisliked = newIsDisliked,
            isLiked = false,
            userDislikes = newUserDislikes,
            userLikes = if (message.isLiked) message.userLikes - 1 else message.userLikes
        )
        dao.updateMessage(updatedMessage)
    }
}