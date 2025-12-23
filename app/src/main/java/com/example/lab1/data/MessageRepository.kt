package com.example.lab1.data

import android.content.Context
import com.example.lab1.data.local.AppDatabase
import com.example.lab1.data.local.MessageEntity
import com.example.lab1.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageRepository(context: Context) {

    private val dao = AppDatabase.getInstance(context).messageDao()

    suspend fun fetchAndSave(): List<MessageEntity> = withContext(Dispatchers.IO) {
        return@withContext try {
            val remote = RetrofitClient.api.getMessages()
            val entities = remote.results.map { MessageEntity(it.id, it.title, it.summary) }
            dao.clear()
            dao.insertAll(entities)
            entities
        } catch (e: Exception) {
            dao.getAll()
        }
    }

    suspend fun getFromDb(): List<MessageEntity> = withContext(Dispatchers.IO) {
        dao.getAll()
    }
}