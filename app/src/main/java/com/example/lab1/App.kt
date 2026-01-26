package com.example.lab1

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.lab1.util.NotificationHelper
import com.example.lab1.worker.SyncWorker
import java.util.concurrent.TimeUnit

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        NotificationHelper.createChannel(this)

        schedulePeriodicSync()
    }

    private fun schedulePeriodicSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val work = PeriodicWorkRequestBuilder<SyncWorker>(
            15,
            TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()



        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "sync_messages",
            ExistingPeriodicWorkPolicy.KEEP,
            work
        )
    }
}
