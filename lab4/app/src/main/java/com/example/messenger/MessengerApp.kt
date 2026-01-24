package com.example.messenger

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.messenger.worker.SyncWorker
import java.util.concurrent.TimeUnit

class MessengerApp : Application() {

    companion object {
        private const val TAG = "MessengerApp"
        const val CHANNEL_ID = "sync_channel"
        const val CHANNEL_NAME = "Синхронизация"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Application onCreate")
        createNotificationChannel()
        runImmediateSync()
        schedulePeriodicSync()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Уведомления о синхронизации данных"
            }
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
            Log.d(TAG, "Notification channel created")
        }
    }

    /**
     * Запускает синхронизацию немедленно при старте приложения
     */
    private fun runImmediateSync() {
        Log.d(TAG, "Scheduling immediate sync...")
        
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val immediateSyncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniqueWork(
            "immediate_sync",
            ExistingWorkPolicy.REPLACE,
            immediateSyncRequest
        )
    }

    /**
     * Планирует периодическую синхронизацию каждые 15 минут (минимальный интервал)
     */
    private fun schedulePeriodicSync() {
        Log.d(TAG, "Scheduling periodic sync...")
        
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicSyncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "periodic_sync",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicSyncRequest
        )
    }
}
