package com.example.messenger.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.messenger.data.entities.MessagesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao {
    @Query("SELECT * FROM messages")
    suspend fun getMessages(): List<MessagesEntity>

    @Query("SELECT * FROM messages ORDER BY id DESC")
    fun observeMessages(): Flow<List<MessagesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMessage(message: MessagesEntity)

    @Query("UPDATE messages SET liked = :isLiked WHERE id = :id")
    suspend fun updateLike(id: Int, isLiked: Boolean)

    @Query("DELETE FROM messages")
    suspend fun deleteAllMessages()
}
