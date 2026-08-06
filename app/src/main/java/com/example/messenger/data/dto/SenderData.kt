package com.example.messenger.data.dto

data class SenderData (
    val id: Int,
    val firstName: String?,
    val lastName: String?,
    val avatarURL: String = ""
)
