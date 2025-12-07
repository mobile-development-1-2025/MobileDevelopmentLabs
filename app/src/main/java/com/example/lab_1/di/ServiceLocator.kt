package com.example.lab_1.di

import android.content.Context
import com.example.lab_1.data.local.AppDatabase
import com.example.lab_1.data.remote.ApiClient
import com.example.lab_1.data.repository.MessageRepository

object ServiceLocator {

    fun provideMessageRepository(context: Context): MessageRepository {
        val database = AppDatabase.getInstance(context)
        val dao = database.messageDao()
        val api = ApiClient.messageApi

        return MessageRepository(api, dao)
    }
}
