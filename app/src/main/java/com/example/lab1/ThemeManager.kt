package com.example.lab1

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

/**
 * Simple helper that stores current theme choice and applies it when needed.
 */
object ThemeManager {
    private const val PREFS_NAME = "app_settings"
    private const val KEY_DARK_THEME = "dark_theme_enabled"

    fun applySavedTheme(context: Context) {
        setNightMode(getPrefs(context).getBoolean(KEY_DARK_THEME, false))
    }

    fun isDarkThemeEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_DARK_THEME, false)
    }

    fun saveAndApply(context: Context, enableDarkTheme: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_DARK_THEME, enableDarkTheme).apply()
        setNightMode(enableDarkTheme)
    }

    private fun getPrefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private fun setNightMode(enableDarkTheme: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (enableDarkTheme) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}

