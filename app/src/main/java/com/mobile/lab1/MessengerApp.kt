package com.mobile.lab1

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mobile.lab1.data.SyncMessagesWorker
import java.util.concurrent.TimeUnit

class MessengerApp : Application() {

    companion object {
        const val CHANNEL_ID_SYNC = "sync_channel"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        scheduleSyncWorker()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Message sync"
            val descriptionText = "Channel for background sync notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID_SYNC, name, importance).apply {
                description = descriptionText
            }
            val notificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    private fun scheduleSyncWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<SyncMessagesWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "SyncMessages",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}