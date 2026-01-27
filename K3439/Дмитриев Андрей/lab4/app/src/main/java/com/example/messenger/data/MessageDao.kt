package com.example.messenger.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * DAO для работы с сообщениями в базе данных Room
 */
@Dao
interface MessageDao {
    
    @Query("SELECT * FROM messages ORDER BY id DESC")
    fun getAllMessagesFlow(): Flow<List<Message>>
    
    @Query("SELECT * FROM messages ORDER BY id DESC")
    suspend fun getAllMessages(): List<Message>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<Message>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)
    
    @Update
    suspend fun updateMessage(message: Message)
    
    @Query("UPDATE messages SET isLiked = :isLiked WHERE id = :messageId")
    suspend fun updateLikeStatus(messageId: Int, isLiked: Boolean)

    @Query("DELETE FROM messages")
    suspend fun deleteAllMessages()
    
    @Query("SELECT COUNT(*) FROM messages")
    suspend fun getMessagesCount(): Int
}
