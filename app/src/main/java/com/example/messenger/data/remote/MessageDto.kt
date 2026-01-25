package com.example.messenger.data.remote

import com.example.messenger.data.local.MessageEntity

data class MessageDto(
    val userId: Long,
    val id: Long,
    val title: String,
    val body: String
)

fun MessageDto.toEntity(): MessageEntity =
    MessageEntity(
        id = id,
        userId = userId,
        title = title,
        body = body
    )

