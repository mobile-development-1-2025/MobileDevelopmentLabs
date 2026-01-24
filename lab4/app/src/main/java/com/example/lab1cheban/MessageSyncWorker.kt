package com.example.lab1cheban.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.lab1cheban.data.AppDatabase
import com.example.lab1cheban.data.MessageRepository
import com.example.lab1cheban.data.RetrofitInstance
import com.example.messenger.R

class MessageSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        private const val CHANNEL_ID = "message_sync_channel"
        private const val NOTIFICATION_ID = 1001
    }

    override suspend fun doWork(): Result {
        return try {
            val repository = MessageRepository(
                RetrofitInstance.api,
                AppDatabase.getDatabase(applicationContext)
            )

            val messages = repository.fetchMessages(page = 1, limit = 10)

            if (messages.isNotEmpty()) {
                showNotification("Новые данные получены",
                    "Синхронизировано ${messages.size} сообщений")
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }

    private fun showNotification(title: String, message: String) {
        val notificationManager = applicationContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Синхронизация сообщений",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Уведомления о синхронизации сообщений"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}