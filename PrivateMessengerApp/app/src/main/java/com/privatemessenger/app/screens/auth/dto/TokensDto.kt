package com.privatemessenger.app.screens.auth.dto

import com.google.gson.annotations.SerializedName

data class TokensDto(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
