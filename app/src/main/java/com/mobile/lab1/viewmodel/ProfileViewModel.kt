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
    val userName: LiveData<String> get() = _userName

    private val _userStatus = MutableLiveData("Онлайн")
    val userStatus: LiveData<String> get() = _userStatus

    init {
        Log.d(TAG, "init: ProfileViewModel создан")
    }

    fun updateName(newName: String) {
        Log.d(TAG, "updateName: $newName")
        _userName.value = newName
    }

    fun updateStatus(newStatus: String) {
        Log.d(TAG, "updateStatus: $newStatus")
        _userStatus.value = newStatus
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: ProfileViewModel уничтожен")
        super.onCleared()
    }
}