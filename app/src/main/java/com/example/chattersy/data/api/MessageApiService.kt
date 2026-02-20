package com.example.chattersy.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface MessageApiService {
    @GET("posts")
    suspend fun getMessages(
        @Query("_start") start: Int = 0,
        @Query("_limit") limit: Int = 25
    ): List<PostApiResponse>
}
