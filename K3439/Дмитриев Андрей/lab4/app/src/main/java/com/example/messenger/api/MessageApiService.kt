package com.example.messenger.api

import com.example.messenger.data.Message
import retrofit2.http.GET

/**
 * API сервис для получения сообщений
 * Использует JSONPlaceholder API
 */
interface MessageApiService {
    @GET("posts")
    suspend fun getMessages(): List<MessageResponse>
}

/**
 * Модель ответа от API (без полей isLiked и avatarColor)
 */
data class MessageResponse(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
) {
    /**
     * Конвертация в модель Message с генерацией цвета аватара
     */
    fun toMessage(): Message {
        return Message(
            id = id,
            userId = userId,
            title = title,
            body = body,
            isLiked = false,
            avatarColor = generateAvatarColor(userId)
        )
    }
    
    companion object {
        private val avatarColors = listOf(
            0xFFE57373.toInt(), // Red
            0xFF81C784.toInt(), // Green
            0xFF64B5F6.toInt(), // Blue
            0xFFFFB74D.toInt(), // Orange
            0xFFBA68C8.toInt(), // Purple
            0xFF4DD0E1.toInt(), // Cyan
            0xFFF06292.toInt(), // Pink
            0xFFAED581.toInt(), // Light Green
            0xFF7986CB.toInt(), // Indigo
            0xFFFFD54F.toInt()  // Amber
        )
        
        fun generateAvatarColor(userId: Int): Int {
            return avatarColors[userId % avatarColors.size]
        }
    }
}
