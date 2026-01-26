package com.example.messengerlab.data.repository

import com.example.messengerlab.data.api.MessengerApi
import com.example.messengerlab.data.db.MessageDao
import com.example.messengerlab.data.model.MessageEntity

class MessageRepository(
    private val api: MessengerApi,
    private val dao: MessageDao
) {

    suspend fun refreshMessages() {
        try {
            val posts = api.getMessages()
            val users = api.getUsers()

            val usersMap = users.associateBy({ it.id }, { it.name })

            val entities = posts.map { post ->
                MessageEntity(
                    id = post.id,
                    userId = post.userId,
                    authorName = usersMap[post.userId] ?: "Unknown",
                    title = post.title,
                    body = post.body
                )
            }

            dao.clearAll()
            dao.insertAll(entities)

        } catch (e: Exception) {
        }
    }

    suspend fun getAllMessages(): List<MessageEntity> =
        dao.getAllMessages()

    suspend fun updateMessage(message: MessageEntity) {
        dao.update(message)
    }
}
