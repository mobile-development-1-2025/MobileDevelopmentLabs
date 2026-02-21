package com.example.messengerlab.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.messengerlab.data.repository.CommentRepository

class NewsSyncWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        const val WORK_NAME = "NewsSyncWorker"
        private const val CHANNEL_ID = "news_sync_channel"
        private const val NOTIFICATION_ID = 2001
        private const val TAG = "NewsSyncWorker"
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "Background sync started")
        return try {
            val repository = CommentRepository(context)
            val items = repository.fetchComments(forceRefresh = true)
            Log.d(TAG, "Synced ${items.size} comments")
            showNotification(items.size)
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Sync failed: ${e.message}")
            Result.retry()
        }
    }

    private fun showNotification(count: Int) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "News Sync",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "Notifications about new data" }
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_menu_info_details)
            .setContentTitle("MessengerLab")
            .setContentText("New data received: $count comments")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        manager.notify(NOTIFICATION_ID, notification)
    }
}