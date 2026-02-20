package com.example.chattersy.data.api

import com.example.chattersy.data.model.Message

data class PostApiResponse(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

fun PostApiResponse.toMessage(isLiked: Boolean = false): Message {
    val avatarUrl = "https://i.pravatar.cc/150?img=${userId % 70 + 1}"
    val userName = "User $userId"
    val likesCount = (id % 100) + 1
    
    return Message(
        id = id,
        avatarUrl = avatarUrl,
        userName = userName,
        text = "$title\n\n$body",
        likesCount = likesCount,
        isLiked = isLiked
    )
}
