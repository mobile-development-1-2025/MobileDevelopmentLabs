package com.example.messenger

import android.app.Application
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.messenger.workers.SyncMessagesWorker
import java.util.concurrent.TimeUnit

class MessengerApplication : Application() {

    companion object {
        private const val TAG = "MessengerApplication"
        private const val SYNC_WORK_NAME = "sync_messages_work"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Приложение запущено")

        setupWorkManager()
    }

    private fun setupWorkManager() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncWorkRequest = PeriodicWorkRequestBuilder<SyncMessagesWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            SYNC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            syncWorkRequest
        )

        Log.d(TAG, "WorkManager настроен: синхронизация каждые 15 минут")
    }
}