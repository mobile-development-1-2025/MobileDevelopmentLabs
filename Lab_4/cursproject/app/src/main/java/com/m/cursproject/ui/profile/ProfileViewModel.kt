package com.m.cursproject.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _userName = MutableLiveData<String>("Иван Иванов")
    val userName: LiveData<String> = _userName

    private val _userStatus = MutableLiveData<String>("Онлайн")
    val userStatus: LiveData<String> = _userStatus

    init {
        Log.d(TAG, "ProfileViewModel: created")
    }

    fun updateUserName(name: String) {
        _userName.value = name
        Log.d(TAG, "User name updated: $name")
    }

    fun updateUserStatus(status: String) {
        _userStatus.value = status
        Log.d(TAG, "User status updated: $status")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ProfileViewModel: cleared")
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}