package com.example.myapplication.data.remote.dto

import com.example.myapplication.data.local.entity.Message
import com.google.gson.annotations.SerializedName

data class MessageDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("body")
    val body: String
) {
    fun toEntity(): Message {
        return Message(
            id = id,
            userId = userId,
            title = title,
            body = body,
            timestamp = System.currentTimeMillis()
        )
    }
}