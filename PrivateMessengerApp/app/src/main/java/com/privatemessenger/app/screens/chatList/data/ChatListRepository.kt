package com.privatemessenger.app.screens.chatList.data

import android.content.Context
import com.privatemessenger.app.common.model.ApiResponse
import com.privatemessenger.app.common.model.UiState
import com.privatemessenger.app.screens.auth.crypto.AesUtils
import com.privatemessenger.app.screens.auth.crypto.RSAUtils
import com.privatemessenger.app.screens.auth.data.AuthRepository
import com.privatemessenger.app.screens.chat.data.ChatRepository
import com.privatemessenger.app.screens.chat.dto.MessageDto
import com.privatemessenger.app.screens.chat.model.MessageModel
import com.privatemessenger.app.screens.chatList.dto.ChatListDto
import com.privatemessenger.app.screens.chatList.model.ChatModel
import com.privatemessenger.app.screens.users.model.toModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ChatListRepository {
    fun getChats(): Flow<UiState<List<ChatModel>>>
}

class ChatListRepositoryImpl @Inject constructor(
    private val usersCloudDataSource: ChatListCloudDataSource,
    @ApplicationContext private val context: Context,
    private val authRepository: AuthRepository,
    private val chatRepository: ChatRepository
) : ChatListRepository {
    override fun getChats(): Flow<UiState<List<ChatModel>>> {
        return usersCloudDataSource.getChats()
            .map { response ->
                when (response) {
                    is ApiResponse.Error -> {
                        UiState.Error(response.toString(context))
                    }
                    is ApiResponse.Success -> {
                        val messageModels = response.data.mapNotNull { dto ->
                            chatRepository.decryptMessage(dto.lastMessage)
                                ?.let { ChatModel(it, dto.peer.toModel()) }
                        }
                        UiState.Success(messageModels)
                    }
                }
            }
            .catch { e ->
                emit(UiState.Error(e.localizedMessage ?: "Unknown error occurred"))
            }
    }

}