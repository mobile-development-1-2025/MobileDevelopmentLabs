package com.example.messenger.network

import retrofit2.http.GET

data class RedditResponse(
    val data: RedditData
)

data class RedditData(
    val children: List<RedditChild>
)

data class RedditChild(
    val data: RedditPost
)

data class RedditPost(
    val id: String,
    val title: String,
    val selftext: String,
    val author: String,
    val url: String
)

interface MessageApi {
    @GET("r/news/top.json?limit=20")
    suspend fun getRedditNews(): RedditResponse
}
