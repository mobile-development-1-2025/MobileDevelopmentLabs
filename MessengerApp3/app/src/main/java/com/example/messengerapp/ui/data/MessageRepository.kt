package com.example.messenger.data

import android.util.Log

class MessageRepository(private val api: MessageApiService, private val db: AppDatabase) {
    private val dao = db.messageDao()

    suspend fun fetchMessages(limit: Int): List<Message> {
        return try {
            Log.d("MessageRepository", "Fetching from API: limit=$limit")

            val response = api.getMessages(limit, 0)
            Log.d("MessageRepository", "Received ${response.posts.size} posts from API")

            // Сохраняем в базу данных
            dao.deleteAll()
            dao.insertMessages(response.posts)

            response.posts
        } catch (e: Exception) {
            Log.e("MessageRepository", "Error fetching from API, loading from DB", e)

            // Если ошибка - загружаем из базы данных
            val cachedMessages = dao.getAllMessages()
            Log.d("MessageRepository", "Loaded ${cachedMessages.size} messages from DB")

            cachedMessages
        }
    }
}