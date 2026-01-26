package com.example.messengerlab.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.messengerlab.MessengerApp
import com.example.messengerlab.notifications.NotificationHelper
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class SyncMessagesWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val repository = (context.applicationContext as MessengerApp).repository

    override suspend fun doWork(): Result {
        if (!isOnline(applicationContext)) {
            return Result.retry()
        }

        return try {
            repository.refreshMessages()
            NotificationHelper.showSyncSuccess(applicationContext)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
