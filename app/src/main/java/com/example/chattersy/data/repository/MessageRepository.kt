package com.example.chattersy.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.chattersy.data.api.PostApiResponse
import com.example.chattersy.data.api.toMessage
import com.example.chattersy.data.database.ChattersyDatabase
import com.example.chattersy.data.database.MessageDao
import com.example.chattersy.data.api.MessageApiService
import com.example.chattersy.data.api.RetrofitClient
import com.example.chattersy.data.model.Message
import com.example.chattersy.data.preferences.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class MessageRepository(context: Context) {
    private val messageDao: MessageDao = ChattersyDatabase.getDatabase(context).messageDao()
    private val apiService: MessageApiService = RetrofitClient.messageApiService
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val prefs = context.getSharedPreferences(AppPreferences.PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val TAG = "MessageRepository"
        private const val PAGE_SIZE = 20
    }

    private fun isNetworkAvailable(): Boolean {
        if (prefs.getBoolean(AppPreferences.KEY_FORCE_OFFLINE, false)) {
            Log.d(TAG, "Force offline mode enabled, skipping network")
            return false
        }
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun isForceOfflineEnabled(): Boolean =
        prefs.getBoolean(AppPreferences.KEY_FORCE_OFFLINE, false)

    fun getMessages(): Flow<List<Message>> {
        return messageDao.getAllMessages()
    }

    suspend fun refreshMessages(): RefreshResult {
        if (!isNetworkAvailable()) {
            val forceOffline = isForceOfflineEnabled()
            Log.d(TAG, "Network not available (forceOffline=$forceOffline), skipping refresh")
            throw NoNetworkException(forceOffline = forceOffline)
        }
        val existing = messageDao.getAllMessages().first()
        val existingLastIds = if (existing.size >= PAGE_SIZE) {
            existing.takeLast(PAGE_SIZE).map { it.id }.toSet()
        } else {
            existing.map { it.id }.toSet()
        }
        var start = 0
        var wasUpToDate = false
        Log.d(TAG, "Fetching messages from API (page size=$PAGE_SIZE)")
        while (true) {
            val chunk = apiService.getMessages(start = start, limit = PAGE_SIZE)
            if (chunk.isEmpty()) {
                wasUpToDate = true
                Log.d(TAG, "Empty page at start=$start, stopping")
                break
            }
            val existingMap = messageDao.getAllMessages().first().associateBy { it.id }
            val messages = chunk.map { post: PostApiResponse ->
                val existingMessage = existingMap[post.id]
                post.toMessage(isLiked = existingMessage?.isLiked ?: false)
            }
            messageDao.insertAll(messages)
            val chunkIds = chunk.map { it.id }.toSet()
            if (existingLastIds.isNotEmpty() && chunkIds == existingLastIds) {
                wasUpToDate = true
                Log.d(TAG, "Page matches last messages, data up to date")
                break
            }
            if (chunk.size < PAGE_SIZE) {
                wasUpToDate = true
                break
            }
            start += chunk.size
        }
        Log.d(TAG, "Messages refreshed, wasUpToDate=$wasUpToDate")
        return RefreshResult(wasUpToDate = wasUpToDate)
    }

    suspend fun loadInitialMessages() {
        val existingMessages = messageDao.getAllMessages().first()
        if (existingMessages.isEmpty() && isNetworkAvailable()) {
            refreshMessages()
        }
    }

    suspend fun likeMessage(id: Int) {
        val message = messageDao.getMessageById(id) ?: return
        val newIsLiked = !message.isLiked
        val newLikesCount = if (newIsLiked) {
            message.likesCount + 1
        } else {
            message.likesCount - 1
        }
        messageDao.updateLikeStatus(id, newIsLiked, newLikesCount)
    }
}
