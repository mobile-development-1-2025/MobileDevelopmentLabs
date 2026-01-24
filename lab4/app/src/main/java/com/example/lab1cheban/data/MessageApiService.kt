package com.example.lab1cheban.data

import retrofit2.http.GET
import retrofit2.http.Query

interface MessageApiService {
    @GET("comments")
    suspend fun getMessages(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int
    ): List<Message>
}
