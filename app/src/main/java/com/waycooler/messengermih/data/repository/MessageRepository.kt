package com.waycooler.messengermih.data.repository

import android.content.Context
import com.waycooler.messengermih.data.local.AppDatabase
import com.waycooler.messengermih.data.local.MessageEntity
import com.waycooler.messengermih.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageRepository(context: Context) {

    private val messageDao = AppDatabase.getInstance(context).messageDao()
    private val api = RetrofitClient.api

    suspend fun getLocalMessages(): List<MessageEntity> = withContext(Dispatchers.IO) {
        messageDao.getAllMessages()
    }

    suspend fun getMessages(): List<MessageEntity> = withContext(Dispatchers.IO) {
        try {
            val remoteMessages = api.getMessages()

            val localMessages = getLocalMessages()
            val localMessagesMap = localMessages.associateBy { it.id }

            val entities = remoteMessages.map { dto ->
                val existingMessage = localMessagesMap[dto.id]
                MessageEntity(
                    id = dto.id,
                    author = dto.title ?: "Пользователь ${dto.id}",
                    text = dto.body ?: "",
                    isLiked = existingMessage?.isLiked ?: false
                )
            }

            messageDao.clearMessages()
            messageDao.insertMessages(entities)

            entities
        } catch (e: Exception) {
            e.printStackTrace()
            getLocalMessages()
        }
    }

    suspend fun toggleLike(messageId: Int) = withContext(Dispatchers.IO) {
        messageDao.toggleLike(messageId)
    }

    suspend fun refreshMessages(): Boolean = withContext(Dispatchers.IO) {
        return@withContext try {
            val remoteMessages = api.getMessages()

            val currentMessages = getLocalMessages()
            val currentMessagesMap = currentMessages.associateBy { it.id }

            val entities = remoteMessages.map { dto ->
                val existingMessage = currentMessagesMap[dto.id]
                MessageEntity(
                    id = dto.id,
                    author = dto.title ?: "Пользователь ${dto.id}",
                    text = dto.body ?: "",
                    isLiked = existingMessage?.isLiked ?: false
                )
            }

            messageDao.clearMessages()
            messageDao.insertMessages(entities)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}