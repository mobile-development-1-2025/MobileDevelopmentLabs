package com.example.messengerlab.ui.settings

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _isDarkTheme = MutableLiveData(false)
    val isDarkTheme: LiveData<Boolean> get() = _isDarkTheme

    fun setTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    init {
        Log.d("SettingsVM", "Создан SettingsViewModel (один раз за всё время)")
    }

    override fun onCleared() {
        Log.d("SettingsVM", "SettingsViewModel уничтожен (когда закрываешь приложение)")
        super.onCleared()
    }
}