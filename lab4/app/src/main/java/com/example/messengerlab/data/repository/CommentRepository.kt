package com.example.messengerlab.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.messengerlab.data.local.AppDatabase
import com.example.messengerlab.data.local.CommentEntity
import com.example.messengerlab.data.remote.NetworkClient

class CommentRepository(private val context: Context) {

    private val TAG = "CommentRepository"
    private val dao = AppDatabase.getInstance(context).commentDao()

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun fetchComments(forceRefresh: Boolean = false): List<CommentEntity> {
        if (forceRefresh || isOnline()) {
            try {
                Log.d(TAG, "Fetching from network...")
                val dtos = NetworkClient.service.getComments().comments
                val entities = dtos.map {
                    CommentEntity(
                        id = it.id,
                        postId = it.postId,
                        name = it.user.fullName,
                        email = it.user.username,
                        body = it.body
                    )
                }
                dao.clear()
                dao.insertAll(entities)
                Log.d(TAG, "Saved ${entities.size} comments to DB")
                return entities
            } catch (e: Exception) {
                Log.e(TAG, "Network error, falling back to DB: ${e.message}")
            }
        } else {
            Log.d(TAG, "Offline, loading from DB...")
        }
        return dao.getAll()
    }

    @androidx.annotation.RequiresApi(android.os.Build.VERSION_CODES.M)
    private fun isOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val caps = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}