package com.privatemessenger.app.screens.auth.dto

import com.google.gson.annotations.SerializedName

data class KeyDto (
    @SerializedName("id")
    val id: Int,
    @SerializedName("public_key")
    val publicKey: String
)