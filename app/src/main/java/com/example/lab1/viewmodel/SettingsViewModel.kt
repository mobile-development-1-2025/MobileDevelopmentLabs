package com.example.lab1.viewmodel

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.core.content.edit

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val PREFS_NAME = "app_prefs"
        private const val KEY_DARK_THEME = "dark_theme"
    }

    private val prefs = application.getSharedPreferences(PREFS_NAME, 0)

    private val _isDarkTheme = MutableLiveData<Boolean>()
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    init {
        val isDark = prefs.getBoolean(KEY_DARK_THEME, false)
        _isDarkTheme.value = isDark
        applyTheme(isDark)
    }

    fun updateTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        prefs.edit { putBoolean(KEY_DARK_THEME, isDark) }
        applyTheme(isDark)
    }

    private fun applyTheme(isDark: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}
