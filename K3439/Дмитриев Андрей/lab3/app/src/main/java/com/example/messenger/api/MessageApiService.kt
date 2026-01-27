package com.example.messenger.api

import com.example.messenger.data.Message
import retrofit2.http.GET

interface MessageApiService {
    @GET("posts")
    suspend fun getMessages(): List<Message>
}
