package com.example.messengerlab1

import android.app.Application
import androidx.room.Room
import com.example.messengerlab1.data.api.RetrofitClient
import com.example.messengerlab1.data.db.AppDatabase
import com.example.messengerlab1.data.repo.MessageRepository

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
    }
}
