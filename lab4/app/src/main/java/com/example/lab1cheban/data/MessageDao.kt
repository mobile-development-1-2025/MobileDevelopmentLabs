package com.example.lab1cheban.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<Message>)

    @Query("SELECT * FROM messages ORDER BY id DESC")
    suspend fun getAllMessages(): List<Message>

    @Query("DELETE FROM messages")
    suspend fun clearMessages()
}
