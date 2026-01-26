package com.example.messenger.httpClients

import com.example.messenger.data.dto.MessageResponseData
import com.example.messenger.data.dto.SenderData
import retrofit2.http.GET
import retrofit2.http.Path

interface MessagesApi {
    @GET("posts/{id}")
    suspend fun getMessage(@Path("id") id: Int): MessageResponseData

    @GET("users/{id}")
    suspend fun getSender (@Path("id") id: Int): SenderData
}
