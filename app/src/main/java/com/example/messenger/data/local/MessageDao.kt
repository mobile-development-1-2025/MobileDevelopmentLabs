package com.example.mymessenger.data.local

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
    @Query("UPDATE messages SET isLiked = :liked WHERE id = :id")
    suspend fun updateLike(id: Int, liked: Boolean)
    @Query("SELECT * FROM messages WHERE isLiked = 1")
    suspend fun getLikedMessages(): List<MessageEntity>

}

