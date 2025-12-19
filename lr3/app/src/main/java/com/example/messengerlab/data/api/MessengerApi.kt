package com.example.messengerlab.data.api

import com.example.messengerlab.data.model.MessageEntity
import retrofit2.http.GET
import com.example.messengerlab.data.model.PostResponse
import retrofit2.Response
interface MessengerApi {
    @GET("posts")
    suspend fun getMessages(): Response<PostResponse>
}