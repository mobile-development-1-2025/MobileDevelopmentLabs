package com.example.messengerapp.data.worker

import android.content.Context
import androidx.room.Room
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.messengerapp.data.MessageRepository
import com.example.messengerapp.data.local.AppDatabase
import com.example.messengerapp.data.notification.NotificationHelper
import com.example.messengerapp.data.remote.RetrofitClient

class SyncWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "messenger.db").build()
        val repo = MessageRepository(RetrofitClient.api, db.messageDao())

        return try {
            repo.refresh() // обновили кеш
            NotificationHelper.show(applicationContext, "Новые данные получены")
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
