package com.example.messengerlab.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.messengerlab.data.model.MessageEntity

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages")
    suspend fun getAllMessages(): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(messages: List<MessageEntity>)

    @Query("DELETE FROM messages")
    suspend fun clearAll()

    @Query("UPDATE messages SET isLiked = :isLiked WHERE id = :id")
    suspend fun updateLike(id: Int, isLiked: Boolean)

    @Update
    suspend fun update(message: MessageEntity)
}
