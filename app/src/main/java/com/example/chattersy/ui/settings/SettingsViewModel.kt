package com.example.chattersy.ui.settings

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.chattersy.data.preferences.AppPreferences

class SettingsViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "SettingsViewModel"
        private const val KEY_IS_DARK_THEME = "is_dark_theme"
    }

    private val prefs = application.getSharedPreferences(AppPreferences.PREF_NAME, Application.MODE_PRIVATE)

    private val _isDarkTheme = savedStateHandle.getLiveData<Boolean>(KEY_IS_DARK_THEME, false)
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    private val _forceOffline = MutableLiveData(prefs.getBoolean(AppPreferences.KEY_FORCE_OFFLINE, false))
    val forceOffline: LiveData<Boolean> = _forceOffline

    fun setDarkTheme(isDark: Boolean) {
        Log.d(TAG, "setDarkTheme: $isDark")
        savedStateHandle[KEY_IS_DARK_THEME] = isDark
    }

    fun setForceOffline(force: Boolean) {
        Log.d(TAG, "setForceOffline: $force")
        prefs.edit().putBoolean(AppPreferences.KEY_FORCE_OFFLINE, force).apply()
        _forceOffline.value = force
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared")
    }
}
