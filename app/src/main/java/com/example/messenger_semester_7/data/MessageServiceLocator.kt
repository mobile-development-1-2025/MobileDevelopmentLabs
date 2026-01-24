package com.example.messenger_semester_7.data

import android.content.Context
import com.example.messenger_semester_7.data.local.MessageDatabase
import com.example.messenger_semester_7.data.remote.MessageApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MessageServiceLocator {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private val lock = Any()

    @Volatile
    private var repository: MessageRepository? = null
    @Volatile
    private var api: MessageApi? = null
    @Volatile
    private var database: MessageDatabase? = null

    fun provideRepository(context: Context): MessageRepository {
        return repository ?: synchronized(lock) {
            repository ?: MessageRepository(
                messageApi = provideApi(),
                messageDao = provideDatabase(context).messageDao()
            ).also { repository = it }
        }
    }

    private fun provideApi(): MessageApi {
        return api ?: synchronized(lock) {
            api ?: createApi().also { api = it }
        }
    }

    private fun provideDatabase(context: Context): MessageDatabase {
        return database ?: synchronized(lock) {
            database ?: MessageDatabase.build(context).also { database = it }
        }
    }

    private fun createApi(): MessageApi {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MessageApi::class.java)
    }
}

