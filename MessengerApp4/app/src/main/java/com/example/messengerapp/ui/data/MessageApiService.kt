package com.example.messenger.data

import retrofit2.http.GET
import retrofit2.http.Query

interface MessageApiService {
    @GET("posts")
    suspend fun getMessages(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): PostsResponse
}
