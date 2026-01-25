package com.example.mymessenger.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val subtitle: String,
    val body: String,
    val isLiked: Boolean = false
)
