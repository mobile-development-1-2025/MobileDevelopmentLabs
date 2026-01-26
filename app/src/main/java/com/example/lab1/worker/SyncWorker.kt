package com.example.lab1.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.lab1.data.MessageRepository
import com.example.lab1.util.NotificationHelper

class SyncWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    private val repo = MessageRepository(ctx.applicationContext)

    override suspend fun doWork(): Result {
        return try {
            val (_, isNew) = repo.fetchAndSaveWithFlag()
            if (isNew) {
                NotificationHelper.showNotification(applicationContext, "Новые данные получены")
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
