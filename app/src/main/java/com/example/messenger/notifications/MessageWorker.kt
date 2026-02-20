package com.example.messenger.notifications

import android.content.Context
import android.util.Log

import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.messenger.db.MessengerDatabase
import com.example.messenger.httpClients.MessagesClient
import com.example.messenger.repository.MessagesRepository


class MessageWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    companion object {
        const val tag = "MessageWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            Log.d(tag, "Do work...")

            val repository = createRepository()
            val notifier = createNotifier()

            repository.loadMessageFromNetwork()
            notifier.sendNotification()

            Result.success()
        } catch (e: Exception) {
            Log.e(tag, "Worker error: ${e.message}")
            Result.retry()
        }
    }

    private fun createNotifier(): Notifier {
        return Notifier(applicationContext).apply {
            setupNotificationChannel()
        }
    }

    private fun createRepository(): MessagesRepository {
        val db = MessengerDatabase.getInstance(applicationContext)
        return MessagesRepository(
            MessagesClient.api,
            db.MessagesDao(),
            applicationContext
        )
    }
}
