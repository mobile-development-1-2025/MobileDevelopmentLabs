package com.example.chattersy.ui.settings

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class SettingsViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel()
{
    companion object
    {
        private const val TAG = "SettingsViewModel"
        private const val KEY_IS_DARK_THEME = "is_dark_theme"
    }

    private val _isDarkTheme = savedStateHandle.getLiveData<Boolean>(KEY_IS_DARK_THEME, false)
    val isDarkTheme: androidx.lifecycle.LiveData<Boolean> = _isDarkTheme

    fun setDarkTheme(isDark: Boolean)
    {
        Log.d(TAG, "setDarkTheme: $isDark")
        savedStateHandle[KEY_IS_DARK_THEME] = isDark
    }

    override fun onCleared()
    {
        super.onCleared()
        Log.d(TAG, "onCleared")
    }
}
