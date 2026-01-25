package com.example.lab_1.data.remote

import retrofit2.http.GET

interface MessageApi {

    @GET("comments")
    suspend fun getMessages(): List<MessageDto>
}
