package com.example.mobiledevslb1.domain.model

data class Message(
    val id: Int,
    val author: String,
    val text: String,
    val isLiked: Boolean = false
)