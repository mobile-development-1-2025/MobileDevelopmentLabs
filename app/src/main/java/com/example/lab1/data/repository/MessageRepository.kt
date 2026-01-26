package com.example.lab1.data.repository

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import com.example.lab1.data.local.MessageDao
import com.example.lab1.data.local.MessageEntity
import com.example.lab1.data.remote.MessageApi
import kotlinx.coroutines.flow.Flow

class MessageRepository(
    private val context: Context,
    private val dao: MessageDao,
    private val api: MessageApi
) {

    fun getMessages(): Flow<List<MessageEntity>> {
        return dao.getAll()
    }

    suspend fun refresh() {
        val remoteMessages = api.getMessages()

        remoteMessages.forEach { dto ->
            val existing = dao.getById(dto.id)
            if (existing == null) {
                dao.insert(
                    MessageEntity(
                        id = dto.id,
                        title = dto.title,
                        body = dto.body,
                        liked = false
                    )
                )
            } else {
                dao.updateContent(dto.id, dto.title, dto.body)
            }
        }
    }


    suspend fun toggleLike(id: Int) {
        dao.toggleLike(id)
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    private fun hasInternet(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val network = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(network) ?: return false

        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
