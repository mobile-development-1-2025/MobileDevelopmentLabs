package com.example.mobiledevslb1.data.remote

import retrofit2.http.GET

interface MessageApi {

    @GET("comments?postId=1")
    suspend fun getMessages(): List<MessageDto>
}