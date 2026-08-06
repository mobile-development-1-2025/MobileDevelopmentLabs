package com.mobile.lab1.data

import retrofit2.http.GET

interface MessageApi {
    @GET("posts")
    suspend fun getMessages(): List<MessageDto>
}