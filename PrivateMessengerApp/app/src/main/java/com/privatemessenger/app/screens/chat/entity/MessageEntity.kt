package com.privatemessenger.app.screens.chat.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.privatemessenger.app.screens.chat.model.MessageModel

@Entity(
    tableName = "messages"
)
data class MessageEntity(
    @PrimaryKey
    @ColumnInfo(name = "message_id")
    val id: Int,

    @ColumnInfo(name = "sender_id")
    val senderId: Int,

    @ColumnInfo(name = "receiver_id")
    val receiverId: Int,

    @ColumnInfo(name = "message")
    val message: String?,

    @ColumnInfo("file_id")
    val fileId: String?,

    @ColumnInfo("file_extension")
    val fileExtension: String?,

    @ColumnInfo("file_path")
    val filePath: String?,

    @ColumnInfo(name = "created_at")
    val createdAt: Long
)

fun MessageModel.toEntity() = MessageEntity(
    id = id,
    senderId = senderId,
    receiverId = receiverId,
    message = message,
    createdAt = createdAt,
    fileId = fileId,
    filePath = filePath,
    fileExtension = fileExtension
)