package com.example.messenger.ui.settings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    companion object {
        private const val TAG = "SettingsViewModel"
    }

    private val _isDarkTheme = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    init {
        Log.d(TAG, "SettingsViewModel инициализирован")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "SettingsViewModel уничтожен")
    }

    fun setTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        Log.d(TAG, "Тема установлена: ${if (isDark) "темная" else "светлая"}")
    }
}