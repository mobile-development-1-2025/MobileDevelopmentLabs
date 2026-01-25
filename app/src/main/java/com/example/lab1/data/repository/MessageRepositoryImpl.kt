package com.example.lab1.data.repository

import com.example.lab1.data.local.LikesStorage
import com.example.lab1.data.local.dao.MessageDao
import com.example.lab1.data.local.model.toDomain
import com.example.lab1.data.remote.api.DummyJsonApi
import com.example.lab1.data.remote.dto.toEntity
import com.example.lab1.data.remote.pagination.PaginationManager
import com.example.lab1.domain.model.Message
import com.example.lab1.domain.repository.MessageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MessageRepositoryImpl(
    private val api: DummyJsonApi,
    private val messageDao: MessageDao,
    private val likesStorage: LikesStorage,
    private val paginationManager: PaginationManager,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MessageRepository {

    // StateFlow to trigger updates when likes change
    private val likesChangeCounter = MutableStateFlow(0L)

    override val messages: Flow<List<Message>> =
        combine(
            messageDao.observeMessages(),
            likesChangeCounter
        ) { entities, _ ->
            entities.map { entity ->
                entity.toDomain().copy(isLiked = likesStorage.isLiked(entity.id))
            }
        }

    override suspend fun refreshMessages(): Int = withContext(ioDispatcher) {
        // Получаем следующую страницу для загрузки разных данных
        val page = paginationManager.getNextPage()
        val pageSize = paginationManager.getPageSize()
        val skip = page * pageSize
        
        android.util.Log.d("MessageRepository", "Загрузка страницы $page (skip=$skip, limit=$pageSize)")
        
        val remoteComments = api.getComments(limit = pageSize, skip = skip)
        val entities = remoteComments.comments.map { it.toEntity() }
        
        android.util.Log.d("MessageRepository", "Загружено ${entities.size} сообщений со страницы $page")
        
        messageDao.replaceAll(entities)
        return@withContext entities.size
    }

    override suspend fun toggleLike(messageId: Long) = withContext(ioDispatcher) {
        likesStorage.toggleLike(messageId)
        // Trigger flow update when likes change by incrementing counter
        likesChangeCounter.value = likesChangeCounter.value + 1
    }
}

