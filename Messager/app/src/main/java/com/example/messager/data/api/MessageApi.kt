package com.example.messager.data.api

import com.example.messager.data.db.MessageDto
import retrofit2.http.GET

interface MessageApi {

    @GET("comments")
    suspend fun getMessages(): List<MessageDto>
}
