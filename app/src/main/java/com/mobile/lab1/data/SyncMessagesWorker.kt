package com.mobile.lab1.data

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mobile.lab1.MessengerApp
import com.mobile.lab1.R

class SyncMessagesWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val db = AppDatabase.getInstance(applicationContext)
        val repo = MessageRepository(
            api = NetworkModule.api,
            dao = db.messageDao()
        )

        return try {
            repo.loadMessages(forceRefresh = true)
            showNotification()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun showNotification() {
        val builder = NotificationCompat.Builder(
            applicationContext,
            MessengerApp.CHANNEL_ID_SYNC
        )
            .setSmallIcon(R.drawable.ic_notifications_24)
            .setContentTitle("Messenger")
            .setContentText("Новые данные получены")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(applicationContext)
            .notify(1001, builder.build())
    }
}