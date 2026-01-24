package com.example.lab1

import android.app.Application
import androidx.room.Room
import com.example.lab1.data.api.RetrofitClient
import com.example.lab1.data.db.AppDatabase
import com.example.lab1.data.repo.MessageRepository

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
    }
}
