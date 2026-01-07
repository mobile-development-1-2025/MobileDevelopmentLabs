package com.example.messengerlab1.data.api

import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getMessages(): List<MessageDto>
}