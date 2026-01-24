package com.example.mymessenger

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {

    private val TAG = "AppViewModel"

    private val _userName = MutableLiveData("User Name")
    val userName: LiveData<String> get() = _userName

    private val _userEmail = MutableLiveData("email@example.com")
    val userEmail: LiveData<String> get() = _userEmail

    private val _isDarkTheme = MutableLiveData(false)
    val isDarkTheme: LiveData<Boolean> get() = _isDarkTheme

    init {
        Log.d(TAG, "ViewModel created")
    }

    fun updateName(newName: String) {
        _userName.value = newName
    }

    fun updateEmail(newEmail: String) {
        _userEmail.value = newEmail
    }

    fun setDarkTheme(enabled: Boolean) {
        _isDarkTheme.value = enabled
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}
