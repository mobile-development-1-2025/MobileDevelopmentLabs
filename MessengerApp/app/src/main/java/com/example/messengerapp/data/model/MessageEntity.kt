package com.example.messengerapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity (
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val body: String
)
