package com.example.messenger.repository

import android.content.Context
import com.example.messenger.data.AppDatabaseInstance
import com.example.messenger.data.Message
import com.example.messenger.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MessageRepository(context: Context) {
    private val dao = AppDatabaseInstance.getDatabase(context).messageDao()

    val messagesFlow: Flow<List<Message>> = dao.getAllMessagesFlow()

    suspend fun refreshMessages(): Result<Int> = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.api.getRedditNews()
            val messages = response.data.children.map { child ->
                val post = child.data
                Message(
                    id = post.id,
                    title = post.title,
                    body = if (post.selftext.isNotEmpty()) post.selftext else post.url,
                    author = post.author,
                    avatarUrl = "https://www.redditstatic.com/avatars/defaults/v2/avatar_default_1.png",
                    timestamp = System.currentTimeMillis()
                )
            }
            dao.insertAll(messages)
            Result.success(messages.size)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMessages(): List<Message> = withContext(Dispatchers.IO) {
        dao.getAllMessages()
    }

    suspend fun toggleLike(messageId: String, isLiked: Boolean) = withContext(Dispatchers.IO) {
        dao.updateLikeStatus(messageId, isLiked)
    }

    suspend fun getMessageCount(): Int = withContext(Dispatchers.IO) {
        dao.getMessageCount()
    }
}
