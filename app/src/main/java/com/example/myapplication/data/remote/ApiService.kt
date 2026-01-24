package com.example.myapplication.data.remote

import com.example.myapplication.data.remote.dto.MessageDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts")
    suspend fun getAllMessages(): List<MessageDto>

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}