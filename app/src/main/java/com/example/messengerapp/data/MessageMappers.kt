package com.example.messengerapp.data

import com.example.messengerapp.data.local.MessageEntity

fun MessageDto.toEntity() = MessageEntity(
    id = id,
    title = name,
    author = email,
    text = body
)
