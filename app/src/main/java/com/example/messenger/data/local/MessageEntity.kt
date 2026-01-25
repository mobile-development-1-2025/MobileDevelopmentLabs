package com.example.messenger.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: Long,
    val userId: Long,
    val title: String,
    val body: String,
    val isLiked: Boolean = false
)



