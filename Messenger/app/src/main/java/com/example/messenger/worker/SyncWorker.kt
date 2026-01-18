package com.example.messenger.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.messenger.repository.MessageRepository
import com.example.messenger.notification.NotificationManager

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        private const val TAG = "SyncWorker"
    }

    override suspend fun doWork(): ListenableWorker.Result {
        return try {
            Log.d(TAG, "SyncWorker started")
            val repository = MessageRepository(applicationContext)
            val result = repository.refreshMessages()
            
            if (result.isSuccess) {
                val messages = result.getOrNull() ?: emptyList()
                Log.d(TAG, "Sync successful, messages count: ${messages.size}")
                NotificationManager.showSyncNotification(applicationContext, messages.size)
                ListenableWorker.Result.success()
            } else {
                val exception = result.exceptionOrNull()
                Log.e(TAG, "Sync failed", exception)
                ListenableWorker.Result.retry()
            }
        } catch (e: Exception) {
            Log.e(TAG, "SyncWorker error", e)
            ListenableWorker.Result.failure()
        }
    }
}
