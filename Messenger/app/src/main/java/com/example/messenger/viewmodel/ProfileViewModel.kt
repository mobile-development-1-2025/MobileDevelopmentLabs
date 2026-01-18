package com.example.messenger.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    companion object {
        private const val TAG = "ProfileViewModel"
    }

    private val _userName = MutableLiveData<String>().apply {
        value = "Иван Иванов"
    }
    val userName: LiveData<String> = _userName

    private val _userStatus = MutableLiveData<String>().apply {
        value = "В сети"
    }
    val userStatus: LiveData<String> = _userStatus

    init {
        Log.d(TAG, "ViewModel created")
    }

    fun updateUserName(name: String) {
        _userName.value = name
        Log.d(TAG, "UserName updated: $name")
    }

    fun updateUserStatus(status: String) {
        _userStatus.value = status
        Log.d(TAG, "UserStatus updated: $status")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}
