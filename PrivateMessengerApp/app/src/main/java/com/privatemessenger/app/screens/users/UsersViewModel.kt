package com.privatemessenger.app.screens.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.privatemessenger.app.common.model.UiState
import com.privatemessenger.app.screens.users.data.UsersRepository
import com.privatemessenger.app.screens.users.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val usersRepository: UsersRepository
) : ViewModel() {
    private val _users = MutableLiveData<UiState<List<UserModel>>>()
    val users: LiveData<UiState<List<UserModel>>> = _users

    fun loadUsers() {
        _users.value = UiState.Loading()
        usersRepository.getUsers().onEach {
            _users.value = it
        }.launchIn(viewModelScope)
    }
}