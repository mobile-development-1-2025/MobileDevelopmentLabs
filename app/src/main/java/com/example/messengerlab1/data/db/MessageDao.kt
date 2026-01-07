package com.example.messengerlab1.data.db

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

    @Query("UPDATE messages SET liked = :liked WHERE id = :id")
    suspend fun setLiked(id: Int, liked: Boolean)

    @Query("SELECT id, liked FROM messages")
    suspend fun getLikedMap(): List<LikedRow>

    data class LikedRow(val id: Int, val liked: Boolean)
}