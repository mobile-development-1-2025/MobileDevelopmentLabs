package com.example.messengerlab1.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.messengerlab1.util.NotificationHelper
import com.example.messengerlab1.data.db.AppDatabase
import com.example.messengerlab1.data.repository.MessageRepository

class SyncWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val repo = MessageRepository(AppDatabase.Companion.get(applicationContext).messageDao())
        val before = repo.getMessages(forceRefresh = false).size
        val afterList = repo.refreshFromNetworkOrFallback()
        val after = afterList.size
        NotificationHelper.showSyncSuccess(applicationContext)

        return Result.success()
    }
}