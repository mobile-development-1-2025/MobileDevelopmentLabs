package com.example.messenger

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

class MessageRepository(private val context: Context) {
    private val messageDao = AppDatabase.getDatabase(context).messageDao()
    private val apiService = RetrofitClient.apiService

    fun getMessages(): LiveData<List<Message>> = liveData(Dispatchers.IO) {
        try {
            if (NetworkUtils.isNetworkAvailable(context)) {
                val apiMessages = apiService.getPosts()
                val messages = apiMessages.map {
                    Message(
                        id = it.id.toLong(),
                        title = it.title,
                        body = it.body,
                        userId = it.userId
                    )
                }
                messageDao.deleteAllMessages()
                messageDao.insertMessages(messages)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        emitSource(messageDao.getAllMessages())
    }

    suspend fun refreshMessages() {
        try {
            if (NetworkUtils.isNetworkAvailable(context)) {
                val apiMessages = apiService.getPosts()
                val messages = apiMessages.map {
                    Message(
                        id = it.id.toLong(),
                        title = it.title,
                        body = it.body,
                        userId = it.userId
                    )
                }
                messageDao.deleteAllMessages()
                messageDao.insertMessages(messages)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
