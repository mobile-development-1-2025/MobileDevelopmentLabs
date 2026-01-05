package com.example.messenger.httpClient

import retrofit2.http.GET
import retrofit2.http.Query;
import com.example.messenger.data.dto.NewsResponse

const val API_KEY = "54a112608a5e4fa3a228c6f52dbb7904"

interface NewsApi {
    @GET("v2/everything?apiKey=$API_KEY&pageSize=25")
    suspend fun getMainPageNews(
        @Query("q") query: String,
        @Query("language") language: String
    ): NewsResponse
}
