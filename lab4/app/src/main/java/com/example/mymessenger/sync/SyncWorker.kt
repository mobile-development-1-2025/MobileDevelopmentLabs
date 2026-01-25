package com.example.mymessenger.sync

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.mymessenger.data.MessageRepository
import com.example.mymessenger.data.local.AppDatabase
import com.example.mymessenger.notify.NotificationHelper

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val TAG = "SyncWorker"

    override suspend fun doWork(): Result {
        Log.d(TAG, "doWork() start")

        val db = AppDatabase.getInstance(applicationContext)
        val repo = MessageRepository(db)

        return try {
            val fresh = repo.loadMessages(forceRefresh = true)
            Log.d(TAG, "Loaded fresh size=${fresh.size}")

            if (fresh.isNotEmpty()) {
                NotificationHelper.show(applicationContext, "Новые данные получены")
                Log.d(TAG, "Notification shown")
            }

            Result.success()
        } catch (e: Exception) {
            Log.e(TAG, "Sync error", e)

            Result.retry()
        }
    }
}
