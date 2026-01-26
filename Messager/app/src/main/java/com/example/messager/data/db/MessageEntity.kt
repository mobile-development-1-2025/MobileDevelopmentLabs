package com.example.messager.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val body: String,
    val liked: Boolean = false
)