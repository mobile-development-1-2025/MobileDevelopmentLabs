package com.example.mobiledevslb1.data.di

import android.content.Context
import com.example.mobiledevslb1.data.local.AppDatabase
import com.example.mobiledevslb1.data.remote.ApiClient
import com.example.mobiledevslb1.data.repository.MessageRepository

object GetService {

    fun messageRepository(context: Context): MessageRepository {
        val database = AppDatabase.getInstance(context)
        val dao = database.messageDao()
        val api = ApiClient.messageApi

        return MessageRepository(api, dao)
    }
}