package com.example.messager.data.db

data class MessageDto(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)
