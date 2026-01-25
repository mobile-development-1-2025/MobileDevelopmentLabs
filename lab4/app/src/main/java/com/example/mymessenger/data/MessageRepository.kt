package com.example.mymessenger.data

import android.util.Log
import com.example.mymessenger.data.local.AppDatabase
import com.example.mymessenger.data.local.MessageEntity
import com.example.mymessenger.data.remote.RetrofitClient

class MessageRepository(
    private val db: AppDatabase
) {

    private val TAG = "MessageRepository"

    suspend fun loadMessages(forceRefresh: Boolean): List<MessageEntity> {
        val dao = db.messageDao()

        if (!forceRefresh) {
            val cached = dao.getAll()
            if (cached.isNotEmpty()) return cached
        }

        return try {
            val response = RetrofitClient.api.getPosts()
            val mapped = response.posts.map {
                MessageEntity(
                    id = it.id,
                    title = it.title,
                    subtitle = "User ${it.userId}",
                    body = it.body
                )
            }

            dao.clear()
            dao.insertAll(mapped)

            mapped
        } catch (e: Exception) {
            Log.e(TAG, "Load error", e)
            dao.getAll()
        }
    }
    suspend fun toggleLike(id: Int) {
        db.messageDao().toggleLike(id)
    }

}
