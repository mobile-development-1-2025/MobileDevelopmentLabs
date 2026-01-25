package com.example.messengerapp_eliza.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

data class NewsResponse(val posts: List<NewsItemApi>)
data class NewsItemApi(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)

data class UsersResponse(val users: List<UserApi>)
data class UserApi(
    val id: Int,
    val username: String
)

interface ApiService {

    @GET("posts")
    suspend fun getNews(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): NewsResponse

    @GET("users")
    suspend fun getUsers(): UsersResponse
}
