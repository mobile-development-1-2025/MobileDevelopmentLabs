package com.mobile.lab1.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages")
    suspend fun getAllMessages(): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(list: List<MessageEntity>)

    @Query("DELETE FROM messages")
    suspend fun clearMessages()

    @Query("UPDATE messages SET isLiked = :isLiked WHERE id = :id")
    suspend fun updateLike(id: Int, isLiked: Boolean)
}