package com.example.messengerapp

import android.util.Log
import kotlinx.coroutines.flow.Flow

class MessageRepository(
    private val api: MessageApi,
    private val dao: MessageDao,
) {

    val messagesFlow: Flow<List<MessageEntity>> = dao.getAllMessages()

    suspend fun refreshMessages() {
        Log.d("MessageRepository", "refreshMessages: start")
        val remoteMessages = api.getMessages()
        Log.d("MessageRepository", "refreshMessages: loaded ${remoteMessages.size} items")

        val entities = remoteMessages.map { dto ->
            MessageEntity(
                id = dto.id,
                title = dto.title,
                body = dto.body,
            )
        }

        dao.clearAll()
        dao.insertMessages(entities)

        Log.d("MessageRepository", "refreshMessages: saved to DB")
    }
}
