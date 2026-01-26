package com.example.messenger

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            if (!NetworkUtils.isNetworkAvailable(applicationContext)) {
                Log.d("SyncWorker", "No internet -> retry")
                return Result.retry()
            }

            val messageDao = AppDatabase.getDatabase(applicationContext).messageDao()
            val apiService = RetrofitClient.apiService

            val apiMessages = apiService.getPosts()
            val messages = apiMessages.map {
                Message(
                    id = it.id.toLong(),
                    title = it.title,
                    body = it.body,
                    userId = it.userId,
                    isLiked = false
                )
            }

            messageDao.deleteAllMessages()
            messageDao.insertMessages(messages)

            Log.d("SyncWorker", "Sync success, messages: ${messages.size}")
            NotificationHelper.showSyncNotification(applicationContext)

            Result.success()
        } catch (e: Exception) {
            Log.e("SyncWorker", "Sync error: ${e.message}", e)
            Result.retry()
        }
    }
}
