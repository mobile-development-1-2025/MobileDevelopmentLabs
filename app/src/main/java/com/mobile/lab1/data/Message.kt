package com.mobile.lab1.data

data class Message(
    val id: Int,
    val title: String,
    val body: String
)

fun MessageDto.toEntity() = MessageEntity(
    id = id,
    userId = userId,
    title = title,
    body = body
)

fun MessageEntity.toDomain() = Message(
    id = id,
    title = title,
    body = body
)