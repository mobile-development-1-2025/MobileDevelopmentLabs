package com.example.messengerlab1

import android.app.Application
import androidx.room.Room
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.messengerlab1.data.api.RetrofitClient
import com.example.messengerlab1.data.db.AppDatabase
import com.example.messengerlab1.data.repo.MessageRepository
import com.example.messengerlab1.work.SyncWorker
import java.util.concurrent.TimeUnit

class App : Application() {

    lateinit var database: AppDatabase
        private set

    lateinit var messageRepository: MessageRepository
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "messenger_db"
        ).build()

        messageRepository = MessageRepository(
            api = RetrofitClient.api,
            dao = database.messageDao()
        )

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "messages_sync",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )

    }
}
