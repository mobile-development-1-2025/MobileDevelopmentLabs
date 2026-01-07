package com.example.messengerlab1.data.network

import com.example.messengerlab1.data.model.MessageDto
import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getMessages(): List<MessageDto>
}