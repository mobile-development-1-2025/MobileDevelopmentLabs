package com.example.messengerlab1.data.repo

import android.util.Log
import com.example.messengerlab1.data.api.MessageApi
import com.example.messengerlab1.data.db.MessageDao
import com.example.messengerlab1.data.db.MessageEntity

class MessageRepository(
    private val api: MessageApi,
    private val dao: MessageDao
) {
    private val tag = "MessageRepository"

    fun observeMessages() = dao.observeAll()

    suspend fun refresh() {
        Log.d(tag, "refresh: start")
        val remote = api.getMessages()
        val entities = remote.map { MessageEntity(id = it.id, title = it.title, body = it.body) }
        dao.upsertAll(entities)
        Log.d(tag, "refresh: saved ${entities.size} items")
    }
}
