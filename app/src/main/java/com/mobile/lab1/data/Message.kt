package com.mobile.lab1.data

data class Message(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val isLiked: Boolean
) {
    val authorName: String
        get() = "User #$userId"
}

fun MessageDto.toEntity(oldLiked: Boolean = false): MessageEntity =
    MessageEntity(
        id = id,
        userId = userId,
        title = title,
        body = body,
        isLiked = oldLiked
    )

fun MessageEntity.toDomain(): Message =
    Message(
        id = id,
        userId = userId,
        title = title,
        body = body,
        isLiked = isLiked
    )