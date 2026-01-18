package com.example.messenger.api

import com.example.messenger.data.MessageResponse
import retrofit2.http.GET

interface MessageApiService {
    @GET("posts")
    suspend fun getMessages(): List<MessageResponse>
}
