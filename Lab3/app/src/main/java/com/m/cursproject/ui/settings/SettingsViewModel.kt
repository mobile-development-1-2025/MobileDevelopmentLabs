package com.m.cursproject.ui.settings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _isDarkTheme = MutableLiveData<Boolean>(false)
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    init {
        Log.d(TAG, "SettingsViewModel: created")
    }

    fun toggleTheme() {
        val newValue = !(_isDarkTheme.value ?: false)
        _isDarkTheme.value = newValue
        Log.d(TAG, "Theme toggled to: ${if (newValue) "Dark" else "Light"}")
    }

    fun setTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        Log.d(TAG, "Theme set to: ${if (isDark) "Dark" else "Light"}")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "SettingsViewModel: cleared")
    }

    companion object {
        private const val TAG = "SettingsViewModel"
    }
}