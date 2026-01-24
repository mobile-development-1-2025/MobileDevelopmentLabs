package com.example.lab1misha.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", "android:com.example.lab1misha:v1.0")
                .build()
            chain.proceed(request)
        }
        .build()

    private const val BASE_URL = "https://www.reddit.com/"

    val api: MessageApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MessageApi::class.java)
    }
}
