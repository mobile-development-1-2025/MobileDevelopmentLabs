package com.example.lab1.domain.model

data class Message(
    val id: Long,
    val author: String,
    val body: String,
    val postId: Long
)

