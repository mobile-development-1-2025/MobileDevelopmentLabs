package com.example.messenger_semester_7.data.remote

import com.example.messenger_semester_7.data.local.MessageEntity
import com.google.gson.annotations.SerializedName

data class MessageDto(
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
) {
    fun toEntity(): MessageEntity = MessageEntity(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}

