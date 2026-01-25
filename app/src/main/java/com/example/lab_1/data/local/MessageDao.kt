package com.example.lab_1.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages ORDER BY id DESC")
    fun getMessagesFlow(): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(messages: List<MessageEntity>)

    @Query("DELETE FROM messages")
    suspend fun clearAll()

    @Query("UPDATE messages SET liked = :liked WHERE id = :id")
    suspend fun setLiked(id: Int, liked: Boolean)

    @Query("SELECT id, liked FROM messages")
    suspend fun getLikedMap(): List<LikedRow>

    data class LikedRow(
        val id: Int,
        val liked: Boolean
    )
}
