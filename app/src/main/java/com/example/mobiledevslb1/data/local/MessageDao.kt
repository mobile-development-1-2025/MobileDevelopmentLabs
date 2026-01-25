package com.example.mobiledevslb1.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages ORDER BY id DESC")
    fun getMessagesAll(): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(messages: List<MessageEntity>)

    @Query("DELETE FROM messages")
    suspend fun clearAll()

    @Query("UPDATE messages SET isLiked = :isLiked WHERE id = :id")
    suspend fun updateLikeStatus(id: Int, isLiked: Boolean)

    @Query("SELECT id FROM messages WHERE isLiked = 1")
    suspend fun getLikedIds(): List<Int>
}