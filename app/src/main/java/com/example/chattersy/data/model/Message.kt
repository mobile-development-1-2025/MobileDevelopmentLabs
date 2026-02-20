package com.example.chattersy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey
    val id: Int,
    val avatarUrl: String,
    val userName: String,
    val text: String,
    val likesCount: Int,
    val isLiked: Boolean
)
