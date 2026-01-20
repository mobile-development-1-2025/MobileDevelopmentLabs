package com.privatemessenger.app.screens.chat.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.privatemessenger.app.common.model.UiState
import com.privatemessenger.app.screens.chat.data.ChatRepository
import com.privatemessenger.app.screens.chat.entity.MessageEntity
import com.privatemessenger.app.screens.users.model.UserModel
import kotlinx.coroutines.flow.last

@OptIn(ExperimentalPagingApi::class)
class ChatRemoteMediator(
    private val peer: UserModel,
    private val chatRepository: ChatRepository
) : RemoteMediator<Int, MessageEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MessageEntity>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 253402202894000

                LoadType.PREPEND -> {
                    val firstItem = state.firstItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    firstItem.createdAt
                }

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    lastItem.createdAt
                }
            }

            val sortOrder = if (loadType == LoadType.PREPEND) "asc" else "desc"

            val loadRes = chatRepository.getMessages(
                peer,
                state.config.pageSize,
                0,
                loadKey,
                sortOrder
            ).last()

            if (loadRes is UiState.Error) {
                return MediatorResult.Error(Exception(loadRes.message))
            }
            val messagesSize = (loadRes as UiState.Success).data.size

            return MediatorResult.Success(
                endOfPaginationReached = messagesSize < state.config.pageSize
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
}