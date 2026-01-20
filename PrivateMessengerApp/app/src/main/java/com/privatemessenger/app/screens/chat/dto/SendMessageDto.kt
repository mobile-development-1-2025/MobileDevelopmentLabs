package com.privatemessenger.app.screens.chat.dto

import com.google.gson.annotations.SerializedName

data class SendMessageDto(
    @SerializedName("receiver_id")
    val receiverId: Int,
    @SerializedName("message")
    val message: String?,
    @SerializedName("keys")
    val keys: List<MessageKeyDto>
)
