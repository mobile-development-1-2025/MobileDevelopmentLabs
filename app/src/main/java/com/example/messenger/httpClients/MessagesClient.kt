package com.example.messenger.httpClients

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object MessagesClient {
    val api: MessagesApi = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MessagesApi::class.java)
}
