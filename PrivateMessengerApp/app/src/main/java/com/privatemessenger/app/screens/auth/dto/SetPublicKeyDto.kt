package com.privatemessenger.app.screens.auth.dto

import com.google.gson.annotations.SerializedName

data class SetPublicKeyDto(
    @SerializedName("key")
    val key: String
)
