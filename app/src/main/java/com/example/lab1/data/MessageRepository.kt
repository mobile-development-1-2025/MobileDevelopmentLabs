package com.example.lab1.data

import android.content.Context
import com.example.lab1.data.local.AppDatabase
import com.example.lab1.data.local.MessageEntity
import com.example.lab1.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageRepository(context: Context) {

    private val dao = AppDatabase.getInstance(context).messageDao()

    suspend fun fetchAndSaveWithFlag(): Pair<List<MessageEntity>, Boolean> = withContext(Dispatchers.IO) {
        return@withContext try {
            val remote = RetrofitClient.api.getMessages()
            val entities = remote.results.map { MessageEntity(it.id, it.title, it.summary) }

            val old = dao.getAll()
            val oldIds = old.map { it.id }.toSet()
            val newIds = entities.map { it.id }.toSet()
            val isNew = newIds.subtract(oldIds).isNotEmpty()

            dao.clear()
            dao.insertAll(entities)
            Pair(entities, isNew)
        } catch (e: Exception) {
            Pair(dao.getAll(), false)
        }
    }

    suspend fun getFromDb(): List<MessageEntity> = withContext(Dispatchers.IO) {
        dao.getAll()
    }

    suspend fun toggleLike(message: MessageEntity) = withContext(Dispatchers.IO) {
        val current = dao.getById(message.id) ?: return@withContext
        dao.updateLike(!current.liked, message.id)
    }
}