package com.example.messenger.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Модель данных сообщения
 * Содержит информацию о сообщении с поддержкой "лайков"
 */
@Entity(tableName = "messages")
data class Message(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val isLiked: Boolean = false,
    val avatarColor: Int = 0 // Цвет аватара для визуального различия пользователей
)
