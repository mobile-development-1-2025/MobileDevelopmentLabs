package com.example.mymessenger.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _userName = MutableLiveData<String>("vae eva")
    private val _userStatus = MutableLiveData<String>("live laugh love")

    val userName: LiveData<String> = _userName
    val userStatus: LiveData<String> = _userStatus

    init {
        Log.d("ViewModelLifecycle", "ProfileViewModel создан")
    }

    fun updateUserName(newName: String) {
        Log.d("ProfileViewModel", "Имя обновлено: $newName")
        _userName.value = newName
    }

    fun updateUserStatus(newStatus: String) {
        Log.d("ProfileViewModel", "Статус обновлен: $newStatus")
        _userStatus.value = newStatus
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModelLifecycle", "ProfileViewModel уничтожен")
    }
}