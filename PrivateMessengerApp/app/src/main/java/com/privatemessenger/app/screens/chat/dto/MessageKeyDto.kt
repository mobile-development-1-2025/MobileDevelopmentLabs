package com.privatemessenger.app.screens.chat.dto

import com.google.gson.annotations.SerializedName

data class MessageKeyDto(
    @SerializedName("receiver_id")
    val receiverId: Int,
    @SerializedName("key")
    val key: String
)
