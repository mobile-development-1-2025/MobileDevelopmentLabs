package com.example.messenger.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey val id: String,
    val title: String,
    val body: String,
    val author: String,
    val avatarUrl: String = "",
    val isLiked: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
