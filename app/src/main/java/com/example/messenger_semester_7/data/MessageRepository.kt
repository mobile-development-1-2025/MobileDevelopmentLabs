package com.example.messenger_semester_7.data

import com.example.messenger_semester_7.data.local.MessageDao
import com.example.messenger_semester_7.data.remote.MessageApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MessageRepository(
    private val messageApi: MessageApi,
    private val messageDao: MessageDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    val messages: Flow<List<Message>> = messageDao.observeMessages().map { entities ->
        entities.map { it.toDomain() }
    }

    suspend fun refreshMessages(): Result<Unit> = withContext(ioDispatcher) {
        try {
            val remoteMessages = messageApi.getMessages()
            val entities = remoteMessages.map { it.toEntity() }
            messageDao.replaceAll(entities)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

