package com.example.lab1.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.lab1.data.notification.NotificationManager
import com.example.lab1.di.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val repository = ServiceLocator.provideMessageRepository(context)
    private val notificationManager = NotificationManager(context)

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            val messageCount = repository.refreshMessages()
            notificationManager.showSyncSuccessNotification(messageCount)

            WorkManagerInitializer.scheduleNextWork(applicationContext)
            
            Result.success()
        } catch (e: Exception) {
            WorkManagerInitializer.scheduleNextWork(applicationContext)
            Result.retry()
        }
    }
}
