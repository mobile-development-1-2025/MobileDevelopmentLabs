package com.privatemessenger.app.screens.chat.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.privatemessenger.app.screens.chat.entity.MessageEntity
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class MessageModel(
    val id: Int,
    val senderId: Int,
    val receiverId: Int,
    val fromMe: Boolean,
    val message: String?,
    val fileId: String?,
    val fileExtension: String?,
    val filePath: String?,
    val createdAt: Long
) : Parcelable

fun MessageEntity.toModel(myId: Int) = MessageModel(
    id = id,
    senderId = senderId,
    receiverId = receiverId,
    message = message,
    createdAt = createdAt,
    fromMe = senderId == myId,
    fileId = fileId,
    fileExtension = fileExtension,
    filePath = filePath
)