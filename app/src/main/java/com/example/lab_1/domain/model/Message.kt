package com.example.lab_1.domain.model

data class Message(
    val id: Int,
    val author: String,
    val text: String,
    val liked: Boolean
)
