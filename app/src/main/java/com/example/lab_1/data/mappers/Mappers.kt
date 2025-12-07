package com.example.lab_1.data.mappers

import com.example.lab_1.data.local.MessageEntity
import com.example.lab_1.data.remote.MessageDto
import com.example.lab_1.domain.model.Message

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
        text = text
    )
