package com.privatemessenger.app.screens.chat.data

import com.privatemessenger.app.common.data.AbstractBaseLocalDataSource
import com.privatemessenger.app.common.model.ApiResponse
import com.privatemessenger.app.screens.chat.dao.ChatDao
import com.privatemessenger.app.screens.chat.entity.toEntity
import com.privatemessenger.app.screens.chat.model.MessageModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ChatLocalDataSource {
    fun insertMessage(
        message: MessageModel
    ): Flow<ApiResponse<Unit>>

    fun insertMessageList(
        list: List<MessageModel>
    ): Flow<ApiResponse<Unit>>

    fun setFilePath(messageId: Int, filePath: String): Flow<ApiResponse<Unit>>
}

class ChatLocalDataSourceImpl @Inject constructor(
    private val chatDao: ChatDao
) : AbstractBaseLocalDataSource(), ChatLocalDataSource {
    override fun insertMessage(message: MessageModel): Flow<ApiResponse<Unit>> = safeDbCall {
        chatDao.insertMessage(message.toEntity())
    }

    override fun insertMessageList(list: List<MessageModel>): Flow<ApiResponse<Unit>> = safeDbCall {
        chatDao.insertMessageList(
            list.map { it.toEntity() }
        )
    }

    override fun setFilePath(messageId: Int, filePath: String): Flow<ApiResponse<Unit>> =
        safeDbCall {
            chatDao.setFilePath(messageId, filePath)
        }
}