package com.privatemessenger.app.screens.chat.dto

import com.google.gson.annotations.SerializedName

data class MessageDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("sender_id")
    val senderId: Int,
    @SerializedName("receiver_id")
    val receiverId: Int,
    @SerializedName("message")
    val message: String?,
    @SerializedName("file_id")
    val fileId: String?,
    @SerializedName("file_extension")
    val fileExtension: String?,
    @SerializedName("created_at")
    val createdAt: Long,
    @SerializedName("key")
    val key: String
)
