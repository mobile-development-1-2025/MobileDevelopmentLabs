package com.example.messengerapp.data.remote

import retrofit2.http.GET

interface MessageApiService {
    @GET("posts")
    suspend fun getMessages(): List<MessageDto>
}
