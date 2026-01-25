package com.example.mymessenger.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("posts")
    suspend fun getPosts(@Query("limit") limit: Int = 30): PostsResponseDto
}
