package com.example.messengerapp_eliza.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.ListenableWorker.Result
import com.example.messengerapp_eliza.data.AppDatabase
import com.example.messengerapp_eliza.data.NewsRepository
import com.example.messengerapp_eliza.util.NotificationUtil

class NewsSyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val db = AppDatabase.getDatabase(applicationContext)
            val repo = NewsRepository(
                applicationContext,
                db.newsDao()
            )

            repo.refreshNews()

            NotificationUtil.showNotification(
                applicationContext,
                "Новости обновлены"
            )

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
