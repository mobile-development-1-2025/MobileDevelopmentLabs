package com.example.lab1.data.remote.api

import com.example.lab1.data.remote.dto.CommentsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyJsonApi {
    @GET("comments")
    suspend fun getComments(
        @Query("limit") limit: Int = 30,
        @Query("skip") skip: Int = 0
    ): CommentsResponseDto
}

