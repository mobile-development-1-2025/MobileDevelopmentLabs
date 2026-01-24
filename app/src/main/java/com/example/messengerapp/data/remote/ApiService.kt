package com.example.messengerapp.data.remote

import com.example.messengerapp.data.MessageDto
import retrofit2.http.GET

interface ApiService {
    @GET("comments")
    suspend fun getMessages(): List<MessageDto>
}
