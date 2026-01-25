package com.example.mobiledevslb1.data.mappers

import com.example.mobiledevslb1.data.local.MessageEntity
import com.example.mobiledevslb1.data.remote.MessageDto
import com.example.mobiledevslb1.domain.model.Message

fun MessageDto.toEntity(): MessageEntity =
    MessageEntity(
        id = id,
        author = email,
        text = body
    )

fun MessageEntity.toDomain(): Message =
    Message(
        id = id,
        author = author,
        text = text,
        isLiked = isLiked
    )