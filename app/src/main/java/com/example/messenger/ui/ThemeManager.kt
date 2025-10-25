package com.example.messenger.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.messenger.R

class ThemeManager(private val context: Context) {
    companion object {
        const val THEME_LIGHT = "light"
        const val THEME_DARK = "dark"
        const val THEME_SYSTEM = "system"
        private const val KEY_THEME = "app_theme"
    }

    private val prefs: SharedPreferences =
        context.getSharedPreferences(KEY_THEME, Context.MODE_PRIVATE)

    var currentTheme: String
        get() = prefs.getString(KEY_THEME, THEME_SYSTEM) ?: THEME_SYSTEM
        set(value) {
            prefs.edit().putString(KEY_THEME, value).apply()
        }

    private val isSystemInDarkTheme: Boolean
        get() = currentTheme == THEME_DARK

    fun applyTheme() {
        when (currentTheme) {
            THEME_LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            THEME_DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            THEME_SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    fun getBottomNavBackgroundColor(): Int {
        return when (currentTheme) {
            THEME_LIGHT -> ContextCompat.getColor(context, R.color.white)
            THEME_DARK -> ContextCompat.getColor(context, R.color.black)
            else -> {
                if (isSystemInDarkTheme) {
                    ContextCompat.getColor(context, R.color.black)
                } else {
                    ContextCompat.getColor(context, R.color.white)
                }
            }
        }
    }
}
