package com.example.messengerlab.data.api

import com.example.messengerlab.data.model.PostDto
import com.example.messengerlab.data.model.UserDto
import retrofit2.http.GET

interface MessengerApi {

    @GET("posts")
    suspend fun getMessages(): List<PostDto>

    @GET("users")
    suspend fun getUsers(): List<UserDto>
}
