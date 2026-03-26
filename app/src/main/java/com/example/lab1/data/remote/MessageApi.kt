package com.example.lab1.data.remote

import com.example.lab1.data.model.MessageDto
import retrofit2.http.GET

interface MessageApi {

    @GET("posts")
    suspend fun getMessages(): List<MessageDto>
}
