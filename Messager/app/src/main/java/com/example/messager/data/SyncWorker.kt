package com.example.messager.data

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.messager.R
import com.example.messager.data.api.RetrofitClient
import com.example.messager.data.db.AppDatabase

class SyncWorker(ctx: Context, params: WorkerParameters)
    : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {

        val db = AppDatabase.getInstance(applicationContext)
        val repo = MessageRepository(RetrofitClient.api, db.messageDao())

        repo.refresh()

        NotificationCompat.Builder(applicationContext,"sync")
            .setSmallIcon(R.drawable.ic_notify)
            .setContentTitle("Messager")
            .setContentText("Новые данные получены")
            .build()

        return Result.success()
    }
}
