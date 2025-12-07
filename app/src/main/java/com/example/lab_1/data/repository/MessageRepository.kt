package com.example.lab_1.data.repository

import android.util.Log
import com.example.lab_1.data.local.MessageDao
import com.example.lab_1.data.local.MessageEntity
import com.example.lab_1.data.remote.MessageApi
import com.example.lab_1.domain.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.lab_1.data.mappers.toDomain
import com.example.lab_1.data.mappers.toEntity


class MessageRepository(
    private val api: MessageApi,
    private val dao: MessageDao
) {

    val messagesFlow: Flow<List<Message>> =
        dao.getMessagesFlow().map { list ->
            list.map { it.toDomain() }
        }

    suspend fun refreshMessages(): Result<Unit> {
        return try {
            val dto = api.getMessages()
            val entities = dto.map { it.toEntity() }

            dao.clearAll()
            dao.insertAll(entities)

            Log.i("MessageRepository", "Loaded ${entities.size} messages from API")

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("MessageRepository", "Failed to refresh", e)
            Result.failure(e)
        }
    }
}
