package com.example.mymessenger.data

import android.content.Context

object UserPrefs {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_NAME = "key_name"
    private const val KEY_STATUS = "key_status"

    fun loadName(context: Context): String =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_NAME, "User") ?: "User"

    fun loadStatus(context: Context): String =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_STATUS, "Online") ?: "Online"

    fun saveName(context: Context, name: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_NAME, name)
            .apply()
    }

    fun saveStatus(context: Context, status: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_STATUS, status)
            .apply()
    }
}