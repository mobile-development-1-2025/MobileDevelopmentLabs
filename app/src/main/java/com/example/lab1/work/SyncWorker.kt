package com.example.lab1.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.lab1.App

class SyncWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            val app = applicationContext as App
            app.messageRepository.refresh()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
