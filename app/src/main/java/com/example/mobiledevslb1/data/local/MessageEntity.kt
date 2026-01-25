package com.example.mobiledevslb1.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: Int,
    val author: String,
    val text: String,
    val isLiked: Boolean = false
)