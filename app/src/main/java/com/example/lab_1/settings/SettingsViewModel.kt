package com.example.lab_1.ui.settings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _isDarkTheme = MutableLiveData(false)
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    init { Log.i("SettingsViewModel", "init") }

    fun setDarkTheme(enabled: Boolean) {
        if (enabled != _isDarkTheme.value) {
            _isDarkTheme.value = enabled
            Log.i("SettingsViewModel", "setDarkTheme: $enabled")
        }
    }

    override fun onCleared() {
        Log.i("SettingsViewModel", "onCleared")
        super.onCleared()
    }
}
