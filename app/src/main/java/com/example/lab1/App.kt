package com.example.lab1

import android.app.Application
import androidx.room.Room
import com.example.lab1.data.api.RetrofitClient
import com.example.lab1.data.db.AppDatabase
import com.example.lab1.data.repo.MessageRepository
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import com.example.lab1.work.SyncWorker

class App : Application() {

    lateinit var messageRepository: MessageRepository
        private set

    override fun onCreate() {
        super.onCreate()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "lab1_db"
        ).build()

        messageRepository = MessageRepository(
            api = RetrofitClient.api,
            dao = db.messageDao()
        )

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<SyncWorker>(10, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "messages_sync",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )

    }
}
