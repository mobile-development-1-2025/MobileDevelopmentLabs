package com.example.messenger.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)
