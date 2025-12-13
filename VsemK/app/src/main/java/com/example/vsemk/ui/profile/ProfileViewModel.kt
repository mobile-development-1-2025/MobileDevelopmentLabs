package com.example.vsemk.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    companion object {
        private const val TAG = "ProfileViewModel"
    }

    private val _userName = MutableLiveData<String>().apply {
        value = "Семен"
    }
    val userName: LiveData<String> = _userName

    private val _userStatus = MutableLiveData<String>().apply {
        value = "https://t.me/Semenyshka"
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
        Log.d(TAG, "onCleared: ViewModel уничтожена")
    }

    init {
        Log.d(TAG, "init: ViewModel создана")
    }
}

