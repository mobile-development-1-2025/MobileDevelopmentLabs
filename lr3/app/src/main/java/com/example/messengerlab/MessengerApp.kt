package com.example.messengerlab

import android.app.Application
import androidx.room.Room
import com.example.messengerlab.data.api.MessengerApi
import com.example.messengerlab.data.db.AppDatabase
import com.example.messengerlab.data.repository.MessageRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MessengerApp : Application() {

    // База данных
    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "messenger_db").build()
    }

    // API
    private val api by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MessengerApi::class.java)
    }

    // Репозиторий
    val repository by lazy {
        MessageRepository(api, database.messageDao())
    }
}