package com.example.messengerapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    // Имя пользователя
    private val _userName = MutableLiveData("Кристина")
    val userName: LiveData<String> get() = _userName

    // Статус пользователя
    private val _userStatus = MutableLiveData("Онлайн")
    val userStatus: LiveData<String> get() = _userStatus

    // Светлая/тёмная тема
    private val _isDarkThemeEnabled = MutableLiveData(false)
    val isDarkThemeEnabled: LiveData<Boolean> get() = _isDarkThemeEnabled

    init {
        Log.d("MainViewModel", "init: ViewModel создана")
    }

    fun updateName(newName: String) {
        Log.d("MainViewModel", "updateName: $newName")
        _userName.value = newName
    }

    fun updateStatus(newStatus: String) {
        Log.d("MainViewModel", "updateStatus: $newStatus")
        _userStatus.value = newStatus
    }

    fun setDarkThemeEnabled(enabled: Boolean) {
        Log.d("MainViewModel", "setDarkThemeEnabled: $enabled")
        _isDarkThemeEnabled.value = enabled
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MainViewModel", "onCleared: ViewModel уничтожена")
    }
}
