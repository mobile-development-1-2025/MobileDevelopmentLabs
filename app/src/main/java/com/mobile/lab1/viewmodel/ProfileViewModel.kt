package com.mobile.lab1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    companion object {
        private const val TAG = "ProfileViewModel"
    }

    private val _userName = MutableLiveData("Андрей")
    val userName: LiveData<String> = _userName

    private val _userStatus = MutableLiveData("Онлайн")
    val userStatus: LiveData<String> = _userStatus

    init {
        Log.d(TAG, "init: ProfileViewModel created")
    }

    fun updateName(newName: String) {
        _userName.value = newName
    }

    fun updateStatus(newStatus: String) {
        _userStatus.value = newStatus
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: ProfileViewModel destroyed")
        super.onCleared()
    }
}