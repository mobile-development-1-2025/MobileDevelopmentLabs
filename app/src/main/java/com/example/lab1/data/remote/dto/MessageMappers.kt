package com.example.lab1.data.remote.dto

import com.example.lab1.data.local.model.MessageEntity

fun CommentDto.toEntity(): MessageEntity {
    return MessageEntity(
        id = id,
        userName = user.username,
        body = body,
        postId = postId
    )
}

