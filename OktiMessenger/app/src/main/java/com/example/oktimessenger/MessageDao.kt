package com.example.oktimessenger

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MessageDao {

    @Update
    suspend fun updateMessage(message: MessageEntity)

    @Query("SELECT * FROM messages")
    suspend fun getAllMessages(): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<MessageEntity>)

    @Query("DELETE FROM messages")
    suspend fun clearMessages()

}

