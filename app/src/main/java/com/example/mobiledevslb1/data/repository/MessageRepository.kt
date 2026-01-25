package com.example.mobiledevslb1.data.repository

import android.util.Log
import com.example.mobiledevslb1.data.local.MessageDao
import com.example.mobiledevslb1.data.remote.MessageApi
import com.example.mobiledevslb1.domain.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.example.mobiledevslb1.data.mappers.toDomain
import com.example.mobiledevslb1.data.mappers.toEntity


class MessageRepository(
    private val api: MessageApi,
    private val dao: MessageDao
) {

    val messages: Flow<List<Message>> =
        dao.getMessagesAll().map { list ->
            list.map { it.toDomain() }
        }

    suspend fun toggleLike(message: Message) {
        dao.updateLikeStatus(message.id, !message.isLiked)
    }

    suspend fun refreshMessages(): Result<Unit> {
        return try {
            Log.d("REPO", "Начинаю качать...")
            val dto = api.getMessages()
            Log.d("REPO", "Скачал ${dto.size} объектов. Начинаю маппинг...")

            val likedIds = dao.getLikedIds().toSet()
            Log.d("REPO", "Найдено лайков в БД: ${likedIds.size}")

            val entities = dto.map { item ->
                val entity = item.toEntity()
                if (likedIds.contains(entity.id)) {
                    entity.copy(isLiked = true)
                } else {
                    entity
                }
            }

            Log.d("REPO", "Маппинг готов. Записываю в БД...")
            dao.insertAll(entities)

            Log.i("MessageRepository", "Loaded ${entities.size} messages from API")

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("MessageRepository", "Failed to refresh", e)
            Result.failure(e)
        }
    }
}