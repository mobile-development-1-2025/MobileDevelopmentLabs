package ru.itmo.mobiledev.lab4

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MessageRepository(
    private val api: MessageApi,
    private val dao: MessageDao
) {

    fun observeMessages(): Flow<List<MessageEntity>> = dao.observeAll()

    suspend fun refresh(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val likedMap = dao.getAll().associateBy({ it.id }, { it.isLiked })
            val items = api.getMessages().map { dto ->
                MessageEntity(
                    id = dto.id,
                    title = dto.title,
                    body = dto.body,
                    author = "User ${dto.userId}",
                    isLiked = likedMap[dto.id] ?: false
                )
            }
            dao.replaceAll(items)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun toggleLike(message: MessageEntity) = withContext(Dispatchers.IO) {
        dao.updateLike(message.id, !message.isLiked)
    }
}
