package com.example.messenger.worker

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.messenger.MessengerApp
import com.example.messenger.R
import com.example.messenger.repository.MessageRepository

class SyncWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        private const val TAG = "SyncWorker"
        private const val NOTIFICATION_ID = 1001
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "=== SyncWorker started ===")
        Log.d(TAG, "Run attempt: $runAttemptCount")

        return try {
            val repository = MessageRepository(context)
            
            Log.d(TAG, "Fetching messages from network...")
            val result = repository.refreshMessages()

            if (result.isSuccess) {
                val count = result.getOrDefault(0)
                Log.d(TAG, "✓ Sync successful: $count messages loaded")
                
                // Показываем уведомление
                showNotification(
                    "Синхронизация завершена",
                    "Загружено $count сообщений"
                )
                
                Result.success()
            } else {
                val error = result.exceptionOrNull()
                Log.e(TAG, "✗ Sync failed: ${error?.message}", error)
                
                // Если это первая попытка, попробуем ещё раз
                if (runAttemptCount < 3) {
                    Log.d(TAG, "Will retry...")
                    Result.retry()
                } else {
                    Log.d(TAG, "Max retries reached, marking as failure")
                    Result.failure()
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "✗ Exception during sync: ${e.message}", e)
            Result.failure()
        }
    }

    private fun showNotification(title: String, message: String) {
        // Проверяем разрешение на уведомления (Android 13+)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.w(TAG, "No notification permission - skipping notification")
                return
            }
        }

        try {
            val notification = NotificationCompat.Builder(context, MessengerApp.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_sync)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .build()

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(NOTIFICATION_ID, notification)
            
            Log.d(TAG, "Notification shown: $title")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to show notification: ${e.message}", e)
        }
    }
}
