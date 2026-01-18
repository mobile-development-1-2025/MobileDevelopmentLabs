package com.example.messenger.data

data class MessageResponse(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)
