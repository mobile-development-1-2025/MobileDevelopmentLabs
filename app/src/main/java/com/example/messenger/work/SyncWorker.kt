package com.example.mymessenger.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.mymessenger.data.MessageRepository
import com.example.mymessenger.data.local.AppDatabase
import com.example.mymessenger.utils.NotificationHelper

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val dao = AppDatabase.getDatabase(applicationContext).messageDao()
        val repository = MessageRepository(dao)

        return try {
            repository.getMessages()
            NotificationHelper.showSyncNotification(applicationContext)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
