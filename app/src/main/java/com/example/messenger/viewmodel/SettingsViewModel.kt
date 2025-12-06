package com.example.messenger.viewmodel

import android.util.Log
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.example.messenger.data.SettingsData
import com.example.messenger.ui.ThemeManager


class SettingsViewModel(context: Context): ViewModel() {
    companion object {
        private const val tag: String = "SettingsVM"
        private const val KEY_THEME: String = "app_theme"
    }

    private val gson = Gson()
    private var themeManager: ThemeManager = ThemeManager(context)
    private val prefs: SharedPreferences =
        context.getSharedPreferences(KEY_THEME, Context.MODE_PRIVATE)

    private val _settings = MutableLiveData<SettingsData>()
    val settings: LiveData<SettingsData> = _settings

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    init {
        loadSettings()
        Log.d(tag, "init SettingsViewModel; load settings")
    }

    private fun loadSettings() {
        try {
            val json = prefs.getString("settings", null)
            val settings = if (json != null) {
                gson.fromJson(json, SettingsData::class.java)
            } else {
                SettingsData()
            }
            _settings.value = settings
        } catch (e: Exception) {
            _toastMessage.value = "Error loading settings"
            _settings.value = SettingsData()
        }
    }

    private fun saveSettings(settings: SettingsData) {
        try {
            val json = gson.toJson(settings)
            prefs.edit().putString("settings", json).apply()
            _settings.value = settings
            _toastMessage.value = "Settings saved"
        } catch (e: Exception) {
            _toastMessage.value = "Error saving settings"
        }
    }

    fun resetSettings() {
        try {
            prefs.edit().remove("settings").apply()
            _settings.value = SettingsData()
            _toastMessage.value = "Settings reset to defaults"
        } catch (e: Exception) {
            _toastMessage.value = "Error resetting settings"
        }
    }

    fun updateTheme(theme: String) {
        val current = settings.value ?: SettingsData()
        saveSettings(current.copy(theme = theme))
        themeManager.currentTheme = theme
    }

    fun applyTheme() {
        themeManager.applyTheme()
    }

    fun updateNotifications(enabled: Boolean) {
        val current = _settings.value ?: SettingsData()
        saveSettings(current.copy(notificationsEnabled = enabled))
    }

    fun getCurrentTheme(): String {
        return _settings.value?.theme ?: ThemeManager.THEME_SYSTEM
    }
}
