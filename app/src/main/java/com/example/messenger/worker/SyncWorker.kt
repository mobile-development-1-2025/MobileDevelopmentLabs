package com.example.messenger.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.messenger.data.MessageRepository
import com.example.messenger.data.local.AppDatabase
import com.example.messenger.utils.NotificationHelper

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val db = AppDatabase.getInstance(applicationContext)
            val repository = MessageRepository(db)
            
            repository.refreshMessages()
            NotificationHelper.showSyncNotification(applicationContext)
            
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}

