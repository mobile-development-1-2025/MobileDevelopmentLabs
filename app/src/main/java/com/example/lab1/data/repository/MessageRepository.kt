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
        if (!hasInternet()) return

        val remote = api.getMessages()
        val entities = remote.map {
            MessageEntity(it.id, it.title, it.body)
        }

        dao.clear()
        dao.insertAll(entities)
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
