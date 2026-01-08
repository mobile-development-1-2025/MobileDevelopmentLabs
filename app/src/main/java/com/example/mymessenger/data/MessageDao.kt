package com.example.mymessenger.data

import androidx.room.*
import com.example.mymessenger.model.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages ORDER BY timestamp DESC")
    fun getAllMessages(): Flow<List<Message>>

    @Query("SELECT * FROM messages ORDER BY timestamp DESC")
    suspend fun getAllMessagesSync(): List<Message>

    @Transaction
    suspend fun upsertAll(messages: List<Message>) {
        messages.forEach { message ->
            val existing = getMessageById(message.id)
            if (existing == null) {
                insertMessage(message)
            } else {
                val updatedMessage = message.copy(isLiked = existing.isLiked)
                updateMessage(updatedMessage)
            }
        }
    }

    @Query("SELECT * FROM messages WHERE id = :messageId")
    suspend fun getMessageById(messageId: Int): Message?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(messages: List<Message>)

    @Update
    suspend fun updateMessage(message: Message)

    @Delete
    suspend fun deleteMessage(message: Message)

    @Query("DELETE FROM messages")
    suspend fun deleteAllMessages()
}