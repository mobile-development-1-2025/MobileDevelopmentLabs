package com.privatemessenger.app.screens.chatList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.privatemessenger.app.common.model.UiState
import com.privatemessenger.app.screens.chatList.data.ChatListRepository
import com.privatemessenger.app.screens.chatList.model.ChatModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val usersRepository: ChatListRepository
) : ViewModel() {
    private val _users = MutableLiveData<UiState<List<ChatModel>>>()
    val users: LiveData<UiState<List<ChatModel>>> = _users

    fun loadUsers() {
        _users.value = UiState.Loading()
        usersRepository.getChats().onEach { uiState ->
            _users.value = uiState
        }.launchIn(viewModelScope)
    }
}