package ru.itmo.mobiledev.lab4

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages ORDER BY id DESC")
    fun observeAll(): Flow<List<MessageEntity>>

    @Query("SELECT * FROM messages")
    suspend fun getAll(): List<MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MessageEntity>)

    @Query("UPDATE messages SET isLiked = :liked WHERE id = :id")
    suspend fun updateLike(id: Int, liked: Boolean)

    @Query("DELETE FROM messages")
    suspend fun clear()

    @Transaction
    suspend fun replaceAll(items: List<MessageEntity>) {
        clear()
        insertAll(items)
    }
}
