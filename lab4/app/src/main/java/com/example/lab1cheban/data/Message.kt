package com.example.lab1cheban.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val body: String
)
