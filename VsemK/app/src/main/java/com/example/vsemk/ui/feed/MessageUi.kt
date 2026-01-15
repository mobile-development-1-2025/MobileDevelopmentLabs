package com.example.vsemk.ui.feed

data class MessageUi(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int,
    val isLiked: Boolean
)

