package com.example.messenger.repository

import android.content.Context
import android.util.Log
import com.example.messenger.data.MessagesPrefs
import com.example.messenger.data.dao.MessagesDao
import com.example.messenger.data.dto.MessageData
import com.example.messenger.data.dto.MessageResponseData
import com.example.messenger.data.dto.SenderData
import com.example.messenger.data.entities.MessagesEntity
import com.example.messenger.data.mappers.toDto
import com.example.messenger.data.mappers.toEntity
import com.example.messenger.httpClients.MessagesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MessagesRepository(
    private val api: MessagesApi, private val dao: MessagesDao, context: Context) {
    companion object {
        private const val tag: String = "Messages Repo"
        private const val maxMessageId: Int = 251
    }

    private val prefs = MessagesPrefs(context)

    suspend fun loadMessageFromNetwork(): MessageData {
        val lastId = prefs.getLastMessageId()

        val nextId =
            if (lastId >= maxMessageId) 1 else lastId + 1

        val msgResponse = api.getMessage(nextId)
        val senderId = msgResponse.senderId
        val senderResponse = api.getSender(senderId)

        val msgEntity = saveLoadedMessage(msgResponse, senderResponse)
        Log.d(tag, "Message $msgResponse loaded and saved successfully!")
        prefs.saveLastMessageId(nextId)

        return msgEntity.toDto()
    }

    suspend fun loadMessagesFromDb(): List<MessageData> {
        val entities = dao.getMessages()
        return entities.map { it.toDto() }
    }

    suspend fun toggleLike(message: MessageData) {
        dao.updateLike(
            id = message.id,
            isLiked = !message.liked
        )
    }
    private suspend fun saveLoadedMessage(msg: MessageResponseData, sender: SenderData): MessagesEntity {
        val senderFullName: String = sender.firstName + sender.lastName
        val entity = msg.toEntity(senderFullName)

        dao.addMessage(entity)
        return entity
    }

    fun observeMessages(): Flow<List<MessageData>> =
        dao.observeMessages().map { entities ->
            entities.map { it.toDto() }
        }
}