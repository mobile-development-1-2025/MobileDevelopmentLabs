package com.example.messager.data

import android.util.Log
import com.example.messager.data.api.MessageApi
import com.example.messager.data.db.MessageDao
import com.example.messager.data.db.MessageEntity

class MessageRepository(
    private val api: MessageApi,
    private val dao: MessageDao
) {

    val messages = dao.getAll()

    suspend fun refresh() {
        try {
            val remote = api.getMessages()
            Log.d("Messages", "API returned: ${remote.size} messages")
            dao.insertAll(
                remote.map { MessageEntity(it.id, it.name, it.body) }
            )
        } catch (e: Exception) {
            Log.e("Messages", "Refresh failed", e)
        }
    }

    suspend fun like(id: Int, liked: Boolean) {
        dao.updateLike(id, liked)
    }
}
