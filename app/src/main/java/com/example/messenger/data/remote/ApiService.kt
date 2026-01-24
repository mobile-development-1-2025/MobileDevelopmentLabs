package com.example.mymessenger.data.remote

import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getMessages(): List<MessageDto>
}
