package com.example.messenger

import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<MessageResponse>
}
