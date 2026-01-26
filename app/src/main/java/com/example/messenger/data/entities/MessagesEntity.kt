package com.example.messenger.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessagesEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val senderName: String?,
    val senderAvatarURL: String,
    val text: String?,
    val liked: Boolean = false
)

