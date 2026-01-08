package com.example.mymessenger.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO) {
                val repository = MessageRepository(applicationContext)
                repository.refreshMessages(showNotification = true)
                Result.success()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }
}