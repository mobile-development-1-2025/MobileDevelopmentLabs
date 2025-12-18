package com.example.messenger

import retrofit2.Response
import retrofit2.http.GET

interface MessengerApiService {

    @GET("posts")
    suspend fun getPosts(): Response<List<Message>>

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}