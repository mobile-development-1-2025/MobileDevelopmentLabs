package com.example.lab1misha.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey val id: String,
    val title: String,
    val body: String,
    val author: String,
)
