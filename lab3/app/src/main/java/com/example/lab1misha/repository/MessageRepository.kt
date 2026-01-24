package com.example.lab1misha.data

import android.content.Context
import com.example.lab1misha.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageRepository(context: Context) {
    private val dao = AppDatabaseInstance.getDatabase(context).messageDao()

    suspend fun getMessages(): List<Message> = withContext(Dispatchers.IO) {
        try {
            val response = RetrofitClient.api.getRedditNews()
            val messages = response.data.children.map {
                val post = it.data
                Message(
                    id = post.id,
                    title = post.title,
                    body = if (post.selftext.isNotEmpty()) post.selftext else post.url,
                    author = post.author
                )
            }
            dao.insertAll(messages)
            messages
        } catch (e: Exception) {
            dao.getAllMessages()
        }
    }
}
