package com.example.messengerlab

import android.app.Application
import androidx.room.Room
import androidx.work.*
import com.example.messengerlab.data.api.MessengerApi
import com.example.messengerlab.data.db.AppDatabase
import com.example.messengerlab.data.repository.MessageRepository
import com.example.messengerlab.notifications.NotificationHelper
import com.example.messengerlab.worker.SyncMessagesWorker
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MessengerApp : Application() {

    val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "messenger_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    private val api by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MessengerApi::class.java)
    }

    val repository by lazy {
        MessageRepository(api, database.messageDao())
    }

    override fun onCreate() {
        super.onCreate()

        NotificationHelper.createChannel(this)
        startSyncWorker()
    }

    private fun startSyncWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<SyncMessagesWorker>(
            1, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                30, TimeUnit.SECONDS
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "sync_messages_work",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
