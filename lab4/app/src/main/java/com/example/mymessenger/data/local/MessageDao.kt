package com.example.mymessenger.data.local

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

    @Query("UPDATE messages SET isLiked = :liked WHERE id = :id")
    suspend fun setLiked(id: Int, liked: Boolean)

    @Query("UPDATE messages SET isLiked = CASE WHEN isLiked = 1 THEN 0 ELSE 1 END WHERE id = :id")
    suspend fun toggleLike(id: Int)

}
