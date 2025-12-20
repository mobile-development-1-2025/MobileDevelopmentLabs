package com.example.messenger.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    
    companion object {
        private const val TAG = "ProfileViewModel"
    }
    
    private val _userName = MutableLiveData<String>().apply {
        value = "Пользователь"
    }
    val userName: LiveData<String> = _userName
    
    private val _userStatus = MutableLiveData<String>().apply {
        value = "В сети"
    }
    val userStatus: LiveData<String> = _userStatus
    
    fun updateUserName(name: String) {
        Log.d(TAG, "updateUserName: $name")
        _userName.value = name
    }
    
    fun updateUserStatus(status: String) {
        Log.d(TAG, "updateUserStatus: $status")
        _userStatus.value = status
    }
    
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared")
    }
    
    init {
        Log.d(TAG, "init - ViewModel created")
    }
}


