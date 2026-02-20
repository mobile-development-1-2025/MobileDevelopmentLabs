package com.example.chattersy.ui.profile

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class ProfileViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel()
{
    companion object
    {
        private const val TAG = "ProfileViewModel"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_STATUS = "user_status"
        private const val DEFAULT_USER_NAME = "Пользователь"
        private const val DEFAULT_USER_STATUS = "В сети"
    }

    private val _userName = savedStateHandle.getLiveData<String>(KEY_USER_NAME, DEFAULT_USER_NAME)
    val userName: androidx.lifecycle.LiveData<String> = _userName

    private val _userStatus = savedStateHandle.getLiveData<String>(KEY_USER_STATUS, DEFAULT_USER_STATUS)
    val userStatus: androidx.lifecycle.LiveData<String> = _userStatus

    fun updateUserName(name: String)
    {
        Log.d(TAG, "updateUserName: $name")
        savedStateHandle[KEY_USER_NAME] = name
    }

    fun updateUserStatus(status: String)
    {
        Log.d(TAG, "updateUserStatus: $status")
        savedStateHandle[KEY_USER_STATUS] = status
    }

    override fun onCleared()
    {
        super.onCleared()
        Log.d(TAG, "onCleared")
    }
}
