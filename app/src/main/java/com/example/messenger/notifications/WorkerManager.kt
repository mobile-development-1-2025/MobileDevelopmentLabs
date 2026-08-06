package com.example.messenger.notifications

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager


import androidx.work.PeriodicWorkRequestBuilder
import java.util.concurrent.TimeUnit

class WorkerManager(private val context: Context) {
    fun startImmediateLoad() {
        val request = OneTimeWorkRequestBuilder<MessageWorker>()
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                "ImmediateMessagesWorker",
                ExistingWorkPolicy.KEEP,
                request
            )
    }

    fun scheduleMessageWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<MessageWorker>(
            15, TimeUnit.MINUTES
        ).setConstraints(constraints).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "MessagesWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}
