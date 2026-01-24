package com.example.lab1misha.ui.settings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val TAG = "SettingsViewModel"

    private val _isDarkTheme = MutableLiveData<Boolean>(false)
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    init {
        Log.d(TAG, "ViewModel создана")
    }

    fun setDarkTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        Log.d(TAG, "Тема изменена: ${if (isDark) "темная" else "светлая"}")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel уничтожена")
    }
}