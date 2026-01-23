package com.m.cursproject.data.api

import com.m.cursproject.data.model.Message
import retrofit2.http.GET

interface MessengerApi {

    @GET("posts")
    suspend fun getMessages(): List<Message>

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}