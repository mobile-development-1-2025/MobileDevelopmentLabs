package com.example.messenger_semester_7.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.messenger_semester_7.data.Message

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String
) {
    fun toDomain(): Message = Message(id = id, userId = userId, title = title, body = body)
}

