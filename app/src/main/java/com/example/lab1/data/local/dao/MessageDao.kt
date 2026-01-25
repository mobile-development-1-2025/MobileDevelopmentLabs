package com.example.lab1.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.lab1.data.local.model.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages ORDER BY id DESC")
    fun observeMessages(): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(messages: List<MessageEntity>)

    @Query("DELETE FROM messages")
    suspend fun clearAll()

    @Transaction
    suspend fun replaceAll(messages: List<MessageEntity>) {
        clearAll()
        if (messages.isNotEmpty()) {
            upsertAll(messages)
        }
    }
}

