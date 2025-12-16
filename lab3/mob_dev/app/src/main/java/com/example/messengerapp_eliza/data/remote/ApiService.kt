package com.example.messengerapp_eliza.data.remote

import retrofit2.http.GET

data class NewsResponse(val posts: List<NewsItemApi>)

data class NewsItemApi(val id: Int, val title: String, val body: String)

interface ApiService {
    @GET("posts")
    suspend fun getNews(): NewsResponse
}