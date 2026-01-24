package com.waycooler.messengermih.data.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.waycooler.messengermih.data.notification.NotificationHelper
import com.waycooler.messengermih.data.repository.MessageRepository

class SyncMessagesWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val repository = MessageRepository(context)

    override suspend fun doWork(): Result {
        return try {
            NotificationHelper.createChannel(applicationContext)

            repository.refreshMessages()

            NotificationHelper.showNotification(applicationContext)

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}