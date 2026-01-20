package com.privatemessenger.app.screens.chatList.data

import com.privatemessenger.app.common.data.AbstractBaseCloudDataSource
import com.privatemessenger.app.common.model.ApiResponse
import com.privatemessenger.app.retrofit.api.ServerApi
import com.privatemessenger.app.screens.chat.dto.MessageDto
import com.privatemessenger.app.screens.chat.dto.MessageKeyDto
import com.privatemessenger.app.screens.chat.dto.SendMessageDto
import com.privatemessenger.app.screens.chatList.dto.ChatListDto
import com.privatemessenger.app.screens.users.dto.UserDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ChatListCloudDataSource {
    fun getChats(): Flow<ApiResponse<List<ChatListDto>>>
}

class ChatListCloudDataSourceImpl @Inject constructor(
    private val serverApi: ServerApi
) : AbstractBaseCloudDataSource(), ChatListCloudDataSource {
    override fun getChats(): Flow<ApiResponse<List<ChatListDto>>> = safeApiCall {
        serverApi.getChats()
    }
}