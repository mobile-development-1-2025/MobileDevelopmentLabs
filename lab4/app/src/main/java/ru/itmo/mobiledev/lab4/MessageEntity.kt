package ru.itmo.mobiledev.lab4

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val body: String,
    val author: String,
    val isLiked: Boolean = false
)
