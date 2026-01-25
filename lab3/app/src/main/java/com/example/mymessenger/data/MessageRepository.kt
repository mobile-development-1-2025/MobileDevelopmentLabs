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

        Log.d(TAG, "loadMessages(forceRefresh=$forceRefresh) -> start")

        if (!forceRefresh) {
            val cached = dao.getAll()
            Log.d(TAG, "DB cached size=${cached.size}")
            if (cached.isNotEmpty()) return cached
        }

        return try {
            Log.d(TAG, "Request -> API start")
            val remote = RetrofitClient.api.getPosts(limit = 30).posts
            Log.d(TAG, "API success, size=${remote.size}")

            val mapped = remote.map {
                MessageEntity(
                    id = it.id,
                    title = it.title,
                    subtitle = "userId=${it.userId}",
                    body = it.body
                )
            }

            if (mapped.isEmpty()) {
                Log.w(TAG, "API returned empty list -> keep DB data")
                val cached = dao.getAll()
                Log.d(TAG, "Return DB after empty API: size=${cached.size}")
                return cached
            }

            dao.clear()
            dao.insertAll(mapped)

            Log.d(TAG, "Saved to DB: ${mapped.size}")
            mapped.sortedByDescending { it.id }

        } catch (e: Exception) {
            Log.e(TAG, "API error -> load from DB: ${e.message}", e)
            val cached = dao.getAll()
            Log.d(TAG, "Loaded from DB after fail: size=${cached.size}")
            cached
        }
    }
}
