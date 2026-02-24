package com.example.messengerapp.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val TAG = "ProfileViewModel"

    private val _username = MutableLiveData("Иван Иванов")
    val username: LiveData<String> = _username

    private val _status = MutableLiveData("В сети")
    val status: LiveData<String> = _status

    init {
        Log.d(TAG, "ViewModel created")
    }

    fun updateUsername(name: String) {
        _username.value = name
    }

    fun updateStatus(status: String) {
        _status.value = status
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}