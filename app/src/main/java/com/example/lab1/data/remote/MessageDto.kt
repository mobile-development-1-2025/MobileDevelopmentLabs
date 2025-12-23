package com.example.lab1.data.remote

data class MessageResponse(
    val results: List<MessageDto>
)

data class MessageDto(
    val id: Int,
    val title: String,
    val summary: String
)