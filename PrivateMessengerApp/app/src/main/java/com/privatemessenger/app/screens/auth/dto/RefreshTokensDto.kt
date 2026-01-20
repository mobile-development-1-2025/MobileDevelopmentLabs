package com.privatemessenger.app.screens.auth.dto

import com.google.gson.annotations.SerializedName

data class RefreshTokensDto(
    @SerializedName("refresh_token")
    val refreshToken: String
)
