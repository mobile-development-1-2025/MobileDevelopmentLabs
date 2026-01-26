package com.example.messenger

data class MessageResponse(
    val id: Int,
    val title: String,
    val body: String?,
    val userId: Int
)
