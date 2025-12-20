package com.example.messenger.ui.settings

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    
    companion object {
        private const val TAG = "SettingsViewModel"
    }
    
    private val _isDarkTheme = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme
    
    fun setDarkTheme(isDark: Boolean) {
        Log.d(TAG, "setDarkTheme: $isDark")
        _isDarkTheme.value = isDark
        
        val themeMode = if (isDark) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }
    
    fun initTheme(themeMode: Int) {
        val isDark = themeMode == AppCompatDelegate.MODE_NIGHT_YES
        Log.d(TAG, "initTheme: themeMode=$themeMode, isDark=$isDark")
        _isDarkTheme.value = isDark
    }
    
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared")
    }
    
    init {
        Log.d(TAG, "init - ViewModel created")
    }
}