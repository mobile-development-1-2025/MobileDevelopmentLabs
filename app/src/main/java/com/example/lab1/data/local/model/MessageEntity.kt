package com.example.lab1.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.lab1.domain.model.Message

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: Long,
    val userName: String,
    val body: String,
    val postId: Long
)

fun MessageEntity.toDomain(): Message {
    return Message(
        id = id,
        author = userName,
        body = body,
        postId = postId
    )
}

