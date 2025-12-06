package com.example.lab1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileSettingsViewModel : ViewModel() {

    private val _name = MutableLiveData("Сергей Ребров")
    val name: LiveData<String> = _name

    private val _status = MutableLiveData("Крутой")
    val status: LiveData<String> = _status

    private val _isDarkTheme = MutableLiveData(false)
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    fun updateName(newName: String) {
        _name.value = newName
        Log.d("ProfileSettingsVM", "ViewModel изменил имя")
    }

    fun updateStatus(newStatus: String) {
        _status.value = newStatus
        Log.d("ProfileSettingsVM", "ViewModel изменил статус")
    }

    fun updateTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        Log.d("ProfileSettingsVM", "ViewModel изменил тему")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ProfileSettingsVM", "ViewModel уничтожен")
    }
}

