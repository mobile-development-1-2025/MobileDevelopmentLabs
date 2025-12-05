package com.m.labs_dk_lab1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.m.labs_dk_lab1.util.Logger

class AppViewModel : ViewModel() {

    init {
        Logger.log("AppViewModel created")
    }

    private val _userName = MutableLiveData("User")
    val userName: LiveData<String> = _userName

    private val _userStatus = MutableLiveData("Online")
    val userStatus: LiveData<String> = _userStatus

    private val _isDarkTheme = MutableLiveData(false)
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    fun setUserName(name: String) {
        Logger.log("AppViewModel: setUserName = $name")
        _userName.value = name
    }

    fun setUserStatus(status: String) {
        Logger.log("AppViewModel: setUserStatus = $status")
        _userStatus.value = status
    }

    fun setTheme(isDark: Boolean) {
        Logger.log("AppViewModel: setTheme = $isDark")
        _isDarkTheme.value = isDark
    }

    override fun onCleared() {
        super.onCleared()
        Logger.log("AppViewModel cleared")
    }
}