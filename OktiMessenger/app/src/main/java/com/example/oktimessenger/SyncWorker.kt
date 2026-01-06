package com.example.oktimessenger

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.d("SyncWorker", "doWork started")

        MessageRepository(applicationContext).getMessages()
        NotificationHelper.show(applicationContext)

        Log.d("SyncWorker", "doWork finished")
        return Result.success()
    }
}

