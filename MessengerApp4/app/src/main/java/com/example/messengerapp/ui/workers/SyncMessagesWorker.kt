package com.example.messenger.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.messenger.R
import com.example.messenger.data.AppDatabase
import com.example.messenger.data.MessageRepository
import com.example.messenger.data.RetrofitInstance

class SyncMessagesWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        const val CHANNEL_ID = "sync_channel"
        const val NOTIFICATION_ID = 1
        private const val TAG = "SyncMessagesWorker"
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "Начало фоновой синхронизации")

        return try {
            val repository = MessageRepository(
                RetrofitInstance.api,
                AppDatabase.getDatabase(applicationContext)
            )

            val messagesBefore = repository.getMessagesFlow()
            val messages = repository.fetchMessages(20)

            Log.d(TAG, "Синхронизировано ${messages.size} сообщений")

            showNotification()

            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Ошибка синхронизации", e)
            Result.retry()
        }
    }

    private fun showNotification() {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

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
            .setSmallIcon(R.drawable.ic_sync)
            .setContentTitle("Синхронизация завершена")
            .setContentText("Новые данные получены")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
        Log.d(TAG, "Уведомление 'Новые данные получены' отображено")
    }
}
