package com.example.lab1.data.api

import retrofit2.http.GET

interface MessageApi {

    @GET("posts")
    suspend fun getPosts(): PostsResponseDto
}
