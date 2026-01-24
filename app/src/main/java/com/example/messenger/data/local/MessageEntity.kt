package com.example.mymessenger.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymessenger.R

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val body: String,
    val isLiked: Boolean = false,
    val avatarRes: Int = R.drawable.ic_avatar_placeholder
)

