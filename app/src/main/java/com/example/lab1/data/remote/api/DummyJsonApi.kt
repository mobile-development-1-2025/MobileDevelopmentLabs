package com.example.lab1.data.remote.api

import com.example.lab1.data.remote.dto.CommentsResponseDto
import retrofit2.http.GET

interface DummyJsonApi {
    @GET("comments")
    suspend fun getComments(): CommentsResponseDto
}

