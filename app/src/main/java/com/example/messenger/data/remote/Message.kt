package com.example.messenger.data.remote

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String,
)