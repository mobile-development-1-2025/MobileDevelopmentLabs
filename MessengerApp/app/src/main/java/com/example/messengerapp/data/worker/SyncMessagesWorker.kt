package com.example.messengerapp.data.worker
import com.example.messengerapp.util.NotificationHelper
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.messengerapp.data.local.AppDatabase
import com.example.messengerapp.data.remote.RetrofitClient
import com.example.messengerapp.data.repository.MessageRepository

class SyncMessagesWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val database = AppDatabase.getDatabase(applicationContext)
            val repository = MessageRepository(
                RetrofitClient.api,
                database.messageDao()
            )

            val messages = repository.getMessages()
            repository.saveMessages(messages)
            NotificationHelper.showSyncNotification(applicationContext)
            Result.success()

        } catch (e: Exception) {
            Result.retry()
        }
    }
}
