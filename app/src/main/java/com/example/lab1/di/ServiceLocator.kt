package com.example.lab1.di

import android.content.Context
import com.example.lab1.data.local.AppDatabase
import com.example.lab1.data.remote.api.NetworkModule
import com.example.lab1.data.repository.MessageRepositoryImpl
import com.example.lab1.domain.repository.MessageRepository

object ServiceLocator {

    @Volatile
    private var repository: MessageRepository? = null

    fun provideMessageRepository(context: Context): MessageRepository {
        val appContext = context.applicationContext
        return repository ?: synchronized(this) {
            val database = AppDatabase.getInstance(appContext)
            val newRepository = MessageRepositoryImpl(
                api = NetworkModule.api,
                messageDao = database.messageDao()
            )
            repository = newRepository
            newRepository
        }
    }
}

