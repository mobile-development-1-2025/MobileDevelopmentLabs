package com.example.lab1.data.remote

import retrofit2.http.GET

interface ApiService {
    @GET("articles/?format=json")
    suspend fun getMessages(): MessageResponse
}
