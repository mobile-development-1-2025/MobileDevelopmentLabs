package com.example.lab1.ui.settings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val tag = "SettingsViewModel"

    // true = тёмная тема, false = светлая
    private val _isDarkTheme = MutableLiveData<Boolean>(false)
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    init {
        Log.d(tag, "init: ViewModel настроек создана")
    }

    fun setDarkTheme(enabled: Boolean) {
        Log.d(tag, "setDarkTheme: $enabled")
        _isDarkTheme.value = enabled
    }

    override fun onCleared() {
        Log.d(tag, "onCleared: ViewModel настроек будет уничтожена")
        super.onCleared()
    }
}
