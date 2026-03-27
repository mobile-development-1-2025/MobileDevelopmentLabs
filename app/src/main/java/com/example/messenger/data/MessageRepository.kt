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
            // 1. Пытаемся загрузить из сети
            val apiMessages = RetrofitClient.api.getMessages()

            val entities = apiMessages.map {
                MessageEntity(it.id, it.title, it.body)
            }

            // 2. Сохраняем в базу
            dao.clearMessages()
            dao.insertMessages(entities)

            entities
        } catch (e: Exception) {
            Log.e("Repository", "No internet, loading from DB")
            // 3. Если нет сети — грузим из Room
            dao.getAllMessages()
        }
    }
}
