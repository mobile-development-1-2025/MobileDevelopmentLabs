package com.example.messengerapp.data.remote

import retrofit2.http.GET
import com.example.messengerapp.data.model.MessageEntity

interface ApiService {

    @GET("comments")
    suspend fun getMessages(): List<MessageEntity>
}
