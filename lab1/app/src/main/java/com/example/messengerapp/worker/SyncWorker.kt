package com.example.messengerapp.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.messengerapp.R
import com.example.messengerapp.data.repository.MessageRepository

class SyncWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val WORK_NAME = "SyncWorker"
        const val CHANNEL_ID = "sync_channel"
        const val NOTIFICATION_ID = 1001
        private const val TAG = "SyncWorker"
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "Фоновая синхронизация запущена")
        return try {
            val repository = MessageRepository(context)
            val messages = repository.getMessages(forceRefresh = true)
            Log.d(TAG, "Синхронизировано ${messages.size} сообщений")
            showNotification(messages.size)
            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Ошибка синхронизации: ${e.message}")
            Result.retry()
        }
    }

    private fun showNotification(count: Int) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Синхронизация сообщений",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Уведомления о новых данных"
            }
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_feed)
            .setContentTitle("Messenger App")
            .setContentText("Новые данные получены: $count сообщений")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        manager.notify(NOTIFICATION_ID, notification)
    }
}
