package com.waycooler.messengermih.data.work

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object WorkManagerHelper {

    fun startSync(context: Context) {
        val workRequest =
            PeriodicWorkRequestBuilder<SyncMessagesWorker>(
                15, TimeUnit.MINUTES
            )
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "sync_messages",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
