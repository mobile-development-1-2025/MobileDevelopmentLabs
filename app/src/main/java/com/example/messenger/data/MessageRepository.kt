package com.example.messenger.data

import com.example.messenger.data.local.AppDatabase
import com.example.messenger.data.local.MessageEntity
import com.example.messenger.data.remote.NetworkClient
import com.example.messenger.data.remote.toEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class MessageRepository(
    private val db: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val messageDao = db.messageDao()

    val messages: Flow<List<MessageEntity>> = messageDao.observeMessages()

    suspend fun refreshMessages() = withContext(ioDispatcher) {
        try {
            val remoteMessages = NetworkClient.messageApi.getMessages()
            val entities = remoteMessages.map { dto ->
                val existing = messageDao.getMessageById(dto.id)
                dto.toEntity().copy(isLiked = existing?.isLiked ?: false)
            }
            messageDao.insertAll(entities)
        } catch (_: Exception) {

        }
    }

    suspend fun toggleLike(messageId: Long, isLiked: Boolean) = withContext(ioDispatcher) {
        messageDao.updateLikeStatus(messageId, isLiked)
    }
}



