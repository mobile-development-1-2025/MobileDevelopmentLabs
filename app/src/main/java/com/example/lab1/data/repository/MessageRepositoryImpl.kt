package com.example.lab1.data.repository

import com.example.lab1.data.local.dao.MessageDao
import com.example.lab1.data.local.model.toDomain
import com.example.lab1.data.remote.api.DummyJsonApi
import com.example.lab1.data.remote.dto.toEntity
import com.example.lab1.domain.model.Message
import com.example.lab1.domain.repository.MessageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MessageRepositoryImpl(
    private val api: DummyJsonApi,
    private val messageDao: MessageDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MessageRepository {

    override val messages: Flow<List<Message>> =
        messageDao.observeMessages().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun refreshMessages() = withContext(ioDispatcher) {
        val remoteComments = api.getComments()
        val entities = remoteComments.comments.map { it.toEntity() }
        messageDao.replaceAll(entities)
    }
}

