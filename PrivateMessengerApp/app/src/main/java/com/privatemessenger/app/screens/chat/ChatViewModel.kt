package com.privatemessenger.app.screens.chat

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.paging.map
import com.privatemessenger.app.screens.auth.data.AuthRepository
import com.privatemessenger.app.screens.chat.dao.ChatDao
import com.privatemessenger.app.screens.chat.data.ChatRepository
import com.privatemessenger.app.screens.chat.entity.MessageEntity
import com.privatemessenger.app.screens.chat.model.MessageModel
import com.privatemessenger.app.screens.chat.model.toModel
import com.privatemessenger.app.screens.chat.paging.ChatRemoteMediator
import com.privatemessenger.app.screens.users.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository,
    private val authRepository: AuthRepository,
    private val chatDao: ChatDao
) : ViewModel() {
    private lateinit var pager: Pager<Int, MessageEntity>
    private lateinit var pagingSource: PagingSource<Int, MessageEntity>
    lateinit var messagesFlow: Flow<PagingData<MessageModel>>

    @OptIn(ExperimentalPagingApi::class)
    fun initPaging(peer: UserModel) {
        pager = Pager(
            config = PagingConfig(pageSize = 30),
            remoteMediator = ChatRemoteMediator(peer, chatRepository)
        ) {
            pagingSource = chatDao.getMessages(peer.userId)
            pagingSource
        }

        val myId = authRepository.getAccessTokenModel().id
        messagesFlow = pager.flow.map { value: PagingData<MessageEntity> ->
            value.map { messageEntity ->
                messageEntity.toModel(myId)
            }
        }.cachedIn(viewModelScope)
    }

    fun sendMessage(
        text: String,
        peer: UserModel
    ) {
        chatRepository.sendMessage(peer, text).launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun sendFile(
        uri: Uri,
        peer: UserModel
    ) {
        chatRepository.sendFile(peer, uri).launchIn(CoroutineScope(Dispatchers.IO))
    }
}