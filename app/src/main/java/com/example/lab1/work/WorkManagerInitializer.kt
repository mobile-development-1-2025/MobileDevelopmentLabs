package com.example.lab1.work

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WorkManagerInitializer {
    private const val SYNC_WORK_NAME = "sync_messages_work"
    private const val REPEAT_INTERVAL = 1L // minutes

    fun initialize(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        scheduleFirstWork(context, constraints)
    }

    private fun scheduleFirstWork(context: Context, constraints: Constraints) {
        val workManager = WorkManager.getInstance(context)

        workManager.cancelUniqueWork(SYNC_WORK_NAME)

        val syncWorkRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .setInitialDelay(REPEAT_INTERVAL, TimeUnit.MINUTES)
            .addTag(SYNC_WORK_NAME)
            .build()

        workManager.enqueueUniqueWork(
            SYNC_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            syncWorkRequest
        )
    }

    fun scheduleNextWork(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workManager = WorkManager.getInstance(context)
        
        val nextWorkRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .setInitialDelay(REPEAT_INTERVAL, TimeUnit.MINUTES)
            .addTag(SYNC_WORK_NAME)
            .build()

        workManager.enqueueUniqueWork(
            SYNC_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            nextWorkRequest
        )
    }
}
