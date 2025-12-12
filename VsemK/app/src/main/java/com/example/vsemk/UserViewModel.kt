package com.example.vsemk

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Shared ViewModel that stores user profile data and UI settings.
 * Lives on the activity scope so data survives configuration changes.
 */
class UserViewModel : ViewModel() {

    private val _userName = MutableLiveData("Семен")
    val userName: LiveData<String> = _userName

    private val _status = MutableLiveData("https://t.me/Semenyshka")
    val status: LiveData<String> = _status

    private val _isDarkTheme = MutableLiveData(false)
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    init {
        Log.d("UserViewModel", "init: ViewModel created")
    }

    fun updateName(newName: String) {
        _userName.value = newName
        Log.d("UserViewModel", "updateName: $newName")
    }

    fun updateStatus(newStatus: String) {
        _status.value = newStatus
        Log.d("UserViewModel", "updateStatus: $newStatus")
    }

    fun setDarkTheme(enabled: Boolean) {
        _isDarkTheme.value = enabled
        Log.d("UserViewModel", "setDarkTheme: $enabled")
    }

    override fun onCleared() {
        Log.d("UserViewModel", "onCleared: ViewModel destroyed")
        super.onCleared()
    }
}

