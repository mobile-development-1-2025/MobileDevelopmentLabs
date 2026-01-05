package com.example.messenger.httpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {
    val api: NewsApi = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsApi::class.java)
}