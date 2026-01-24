package com.example.messengerapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages ORDER BY id DESC")
    suspend fun getAll(): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MessageEntity>)

    @Query("DELETE FROM messages")
    suspend fun clear()

    @Query("UPDATE messages SET isLiked = NOT isLiked WHERE id = :id")
    suspend fun toggleLike(id: Int)
}
