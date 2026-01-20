package com.privatemessenger.app.screens.users.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("public_key")
    val publicKey: String
)
