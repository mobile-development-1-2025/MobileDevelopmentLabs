package ru.itmo.mobiledev.lab4

import android.content.Context

object AppPreferences {
    private const val PREFS_NAME = "app_prefs"
    private const val KEY_OFFLINE = "offline_mode"

    fun isOffline(context: Context): Boolean {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_OFFLINE, false)
    }

    fun setOffline(context: Context, value: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_OFFLINE, value)
            .apply()
    }
}
