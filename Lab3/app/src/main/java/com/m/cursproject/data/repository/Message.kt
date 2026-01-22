package com.m.cursproject.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import com.m.cursproject.data.api.RetrofitClient
import com.m.cursproject.data.local.MessengerDatabase
import com.m.cursproject.data.model.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MessageRepository(private val context: Context) {

    private val messageDao = MessengerDatabase.getDatabase(context).messageDao()
    private val api = RetrofitClient.api

    val allMessages: LiveData<List<Message>> = messageDao.getAllMessages()

    suspend fun refreshMessages(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            if (!isNetworkAvailable()) {
                Log.d(TAG, "No network available, loading from cache")
                return@withContext Result.failure(Exception("No network connection"))
            }

            Log.d(TAG, "Fetching messages from API...")
            val messages = api.getMessages()
            Log.d(TAG, "Received ${messages.size} messages from API")

            messageDao.deleteAllMessages()
            messageDao.insertMessages(messages)
            Log.d(TAG, "Messages saved to database")

            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Error refreshing messages", e)
            Result.failure(e)
        }
    }

    suspend fun loadCachedMessages(): List<Message> = withContext(Dispatchers.IO) {
        try {
            val messages = messageDao.getAllMessagesSync()
            Log.d(TAG, "Loaded ${messages.size} messages from cache")
            messages
        } catch (e: Exception) {
            Log.e(TAG, "Error loading cached messages", e)
            emptyList()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    companion object {
        private const val TAG = "MessageRepository"

        @Volatile
        private var instance: MessageRepository? = null

        fun getInstance(context: Context): MessageRepository {
            return instance ?: synchronized(this) {
                instance ?: MessageRepository(context.applicationContext).also { instance = it }
            }
        }
    }
}