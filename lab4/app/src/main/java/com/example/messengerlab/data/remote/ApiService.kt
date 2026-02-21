package com.example.messengerlab.data.remote

import retrofit2.http.GET

interface ApiService {
    @GET("comments")
    suspend fun getComments(): CommentsResponse
}

data class CommentsResponse(
    val comments: List<CommentDto>
)