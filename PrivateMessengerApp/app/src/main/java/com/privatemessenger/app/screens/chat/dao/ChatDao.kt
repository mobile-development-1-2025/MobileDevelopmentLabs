package com.privatemessenger.app.screens.chat.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.privatemessenger.app.screens.chat.entity.MessageEntity

@Dao
abstract class ChatDao {
    @Query("SELECT * FROM messages WHERE (sender_id = :peerId OR receiver_id = :peerId) AND (message IS NOT NULL OR file_path IS NOT NULL) ORDER BY created_at DESC")
    abstract fun getMessages(
        peerId: Int
    ): PagingSource<Int, MessageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMessage(messageEntity: MessageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMessageList(list: List<MessageEntity>)

    @Query("UPDATE messages SET file_path = :filePath WHERE message_id = :messageId")
    abstract suspend fun setFilePath(messageId: Int, filePath: String)
}