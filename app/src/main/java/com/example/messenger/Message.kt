package com.example.messenger

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("receiverId") val receiverId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String,
)