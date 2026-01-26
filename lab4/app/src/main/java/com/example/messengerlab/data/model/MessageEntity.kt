package com.example.messengerlab.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val authorName: String,
    val title: String,
    val body: String,
    val isLiked: Boolean = false
)
