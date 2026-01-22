package com.example.messenger.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    @ColumnInfo(name = "is_liked") val isLiked: Boolean = false,
)