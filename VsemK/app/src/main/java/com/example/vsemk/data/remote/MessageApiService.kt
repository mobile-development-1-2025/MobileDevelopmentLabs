package com.example.vsemk.data.remote

import retrofit2.http.GET

interface MessageApiService {
    
    @GET("posts")
    suspend fun getMessages(): List<MessageResponse>
}

data class MessageResponse(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)


