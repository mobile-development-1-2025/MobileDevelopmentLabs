package com.m.cursproject.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.m.cursproject.R
import com.m.cursproject.data.repository.MessageRepository

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val repository = MessageRepository.getInstance(context)

    override suspend fun doWork(): Result {
        Log.d(TAG, "SyncWorker: Starting background sync")

        return try {
            // Проверяем наличие сети
            if (!repository.isNetworkAvailable()) {
                Log.d(TAG, "SyncWorker: No network available")
                return Result.retry()
            }

            // Выполняем синхронизацию
            val result = repository.refreshMessages()

            if (result.isSuccess) {
                Log.d(TAG, "SyncWorker: Sync successful")
                showSuccessNotification()
                Result.success()
            } else {
                Log.e(TAG, "SyncWorker: Sync failed", result.exceptionOrNull())
                Result.retry()
            }
        } catch (e: Exception) {
            Log.e(TAG, "SyncWorker: Exception during sync", e)
            Result.failure()
        }
    }

    private fun showSuccessNotification() {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Создаем канал уведомлений для Android 8.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Синхронизация сообщений",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Уведомления о синхронизации данных"
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Создаем уведомление
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Новые данные")
            .setContentText("Новые данные получены")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        // Показываем уведомление
        notificationManager.notify(NOTIFICATION_ID, notification)

        Log.d(TAG, "SyncWorker: Notification shown")
    }

    companion object {
        private const val TAG = "SyncWorker"
        private const val CHANNEL_ID = "sync_channel"
        private const val NOTIFICATION_ID = 1001

        const val WORK_NAME = "MessageSyncWork"
    }
}