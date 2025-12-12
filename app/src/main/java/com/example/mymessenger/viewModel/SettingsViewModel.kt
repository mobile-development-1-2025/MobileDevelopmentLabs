package com.example.mymessenger.viewModel

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    companion object {
        private const val PREFS_NAME = "theme_prefs"
        private const val KEY_IS_DARK_THEME = "is_dark_theme"
    }

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedTheme = sharedPreferences.getBoolean(KEY_IS_DARK_THEME, false)

        applyTheme(savedTheme, saveToPrefs = false)

        Log.d("SettingsViewModel", "Инициализировано с темой: ${if (savedTheme) "темная" else "светлая"}")
    }

    private fun applyTheme(isDark: Boolean, saveToPrefs: Boolean = true) {
        val mode = if (isDark) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        viewModelScope.launch {
            AppCompatDelegate.setDefaultNightMode(mode)

            if (saveToPrefs) {
                sharedPreferences.edit().putBoolean(KEY_IS_DARK_THEME, isDark).apply()
            }
        }
    }

    fun setDarkTheme(isDark: Boolean) {
        applyTheme(isDark)
        Log.d("SettingsViewModel", "Тема установлена: ${if (isDark) "темная" else "светлая"}")
    }


    fun isDarkTheme(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_DARK_THEME, false)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ViewModelLifecycle", "SettingsViewModel уничтожен")
    }
}