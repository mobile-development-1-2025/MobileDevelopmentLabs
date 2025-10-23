package com.example.messenger_semester_7

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {
    private const val THEME_PREF_KEY = "dark_theme_enabled"
    private const val PREFS_NAME = "theme_prefs"
    
    fun isDarkThemeEnabled(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(THEME_PREF_KEY, false)
    }
    
    fun setDarkThemeEnabled(context: Context, enabled: Boolean) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(THEME_PREF_KEY, enabled).apply()
    }
    
    fun applyTheme(context: Context) {
        val isDarkTheme = isDarkThemeEnabled(context)
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
