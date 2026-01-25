package com.example.lab1.data.repo

import android.util.Log
import com.example.lab1.data.api.MessageApi
import com.example.lab1.data.db.MessageDao
import com.example.lab1.data.db.MessageEntity

class MessageRepository(
    private val api: MessageApi,
    private val dao: MessageDao
) {
    private val tag = "MessageRepository"

    fun observeMessages() = dao.observeAll()

    suspend fun refresh() {
        Log.d(tag, "refresh: start")

        val response = api.getPosts()
        val entities = response.posts.map {
            MessageEntity(
                id = it.id,
                title = it.title,
                body = it.body
            )
        }

        dao.upsertAll(entities)
        Log.d(tag, "refresh: saved ${entities.size}")
    }
}
