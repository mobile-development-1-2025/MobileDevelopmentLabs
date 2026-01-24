package com.waycooler.messengermih.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages")
    suspend fun getAllMessages(): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<MessageEntity>)

    @Query("DELETE FROM messages")
    suspend fun clearMessages()

    @Query("""
        UPDATE messages 
        SET isLiked = CASE 
            WHEN isLiked = 0 THEN 1 
            ELSE 0 
        END
        WHERE id = :messageId
    """)
    suspend fun toggleLike(messageId: Int)

    @Query("SELECT * FROM messages WHERE id = :messageId")
    suspend fun getMessageById(messageId: Int): MessageEntity?
}
