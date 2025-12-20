package com.example.lab1.domain.repository

import com.example.lab1.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    val messages: Flow<List<Message>>
    suspend fun refreshMessages()
}

