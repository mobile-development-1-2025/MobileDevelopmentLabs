package com.waycooler.messengermih.data.repository

import android.content.Context
import com.waycooler.messengermih.data.local.AppDatabase
import com.waycooler.messengermih.data.local.MessageEntity
import com.waycooler.messengermih.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageRepository(context: Context) {

    private val messageDao = AppDatabase.getInstance(context).messageDao()
    private val api = RetrofitClient.api

    suspend fun getMessages(): List<MessageEntity> = withContext(Dispatchers.IO) {
        try {
            val remoteMessages = api.getMessages()

            val entities = remoteMessages.map {
                MessageEntity(
                    id = it.id,
                    author = "User ${it.id}",
                    text = it.body
                )
            }

            messageDao.clearMessages()
            messageDao.insertMessages(entities)

            entities
        } catch (e: Exception) {
            messageDao.getAllMessages()
        }
    }
}
