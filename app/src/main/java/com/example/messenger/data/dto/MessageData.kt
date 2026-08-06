package com.example.messenger.data.dto

import com.google.gson.annotations.SerializedName

data class MessageResponseData (
    val id: Int,

    @SerializedName("userId")
    val senderId: Int,

    @SerializedName("body")
    val text: String?,
)

data class MessageData (
    val id: Int,
    val text: String?,
    val username: String?,
    val avatarURL: String,
    val liked: Boolean = false
)
