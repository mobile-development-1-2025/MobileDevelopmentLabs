package com.example.lab1.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages")
    fun getAll(): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages WHERE id = :id")
    suspend fun getById(id: Int): MessageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(message: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(messages: List<MessageEntity>)

    @Query("""
        UPDATE messages 
        SET title = :title, body = :body 
        WHERE id = :id
    """)
    suspend fun updateContent(
        id: Int,
        title: String,
        body: String
    )

    @Query("""
        UPDATE messages 
        SET liked = NOT liked 
        WHERE id = :id
    """)
    suspend fun toggleLike(id: Int)

    @Query("DELETE FROM messages")
    suspend fun clear()
}
