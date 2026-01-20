package com.privatemessenger.app.screens.auth.dto

import com.google.gson.annotations.SerializedName

data class AdminKeysDto (
    @SerializedName("keys")
    val keys: List<KeyDto>
)