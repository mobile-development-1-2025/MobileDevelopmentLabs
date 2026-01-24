package com.example.mymessenger.data

import android.util.Log
import com.example.mymessenger.data.local.MessageDao
import com.example.mymessenger.data.local.MessageEntity
import com.example.mymessenger.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageRepository(
    private val dao: MessageDao
) {

    suspend fun getMessages(): List<MessageEntity> = withContext(Dispatchers.IO) {
        try {
            val apiMessages = RetrofitClient.api.getMessages()

            val localMessages = dao.getAllMessages()
            val likedMap = localMessages.associateBy(
                { it.id },
                { it.isLiked }
            )

            val entities = apiMessages.map {
                MessageEntity(
                    id = it.id,
                    title = it.title,
                    body = it.body,
                    isLiked = likedMap[it.id] ?: false
                )
            }

            dao.clearMessages()
            dao.insertMessages(entities)

            entities
        } catch (e: Exception) {
            Log.e("Repository", "No internet, loading from DB")
            dao.getAllMessages()
        }
    }

    suspend fun toggleLike(message: MessageEntity) {
        dao.updateLike(
            id = message.id,
            liked = !message.isLiked
        )
    }

    suspend fun getLikedMessages(): List<MessageEntity> =
        dao.getLikedMessages()


}
