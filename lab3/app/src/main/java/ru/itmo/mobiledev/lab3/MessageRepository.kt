package ru.itmo.mobiledev.lab3

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
            val items = api.getMessages().map { dto ->
                MessageEntity(
                    id = dto.id,
                    title = dto.title,
                    body = dto.body
                )
            }
            dao.replaceAll(items)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
