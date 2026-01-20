package com.privatemessenger.app.screens.chatList.dto

import com.google.gson.annotations.SerializedName
import com.privatemessenger.app.screens.chat.dto.MessageDto
import com.privatemessenger.app.screens.users.dto.UserDto

data class ChatListDto (
    @SerializedName("peer")
    val peer: UserDto,
    @SerializedName("last_message")
    val lastMessage: MessageDto
)