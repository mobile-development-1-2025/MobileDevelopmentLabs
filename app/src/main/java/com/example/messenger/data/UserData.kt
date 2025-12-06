package com.example.messenger.data

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val bio: String = ""
)
