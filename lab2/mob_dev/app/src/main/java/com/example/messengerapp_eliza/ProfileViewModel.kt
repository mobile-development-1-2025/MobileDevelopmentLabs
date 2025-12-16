package com.example.messengerapp_eliza

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val _userName = MutableLiveData<String>("Строганова Елизавета Ивановна")
    val userName: LiveData<String> get() = _userName

    private val _userStatus = MutableLiveData<String>("Online")
    val userStatus: LiveData<String> get() = _userStatus

    private val _isDarkTheme = MutableLiveData<Boolean>(false)
    val isDarkTheme: LiveData<Boolean> get() = _isDarkTheme

    init {
        Log.d("ProfileViewModel", "ViewModel created")
    }

    fun updateUserName(newName: String) {
        _userName.value = newName
        Log.d("ProfileViewModel", "User name updated to: $newName")
    }

    fun updateUserStatus(newStatus: String) {
        _userStatus.value = newStatus
        Log.d("ProfileViewModel", "User status updated to: $newStatus")
    }

    fun toggleDarkTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        Log.d("ProfileViewModel", "Dark theme toggled to: $isDark")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ProfileViewModel", "ViewModel cleared/destroyed")
    }
}