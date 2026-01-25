package com.example.mymessenger.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val TAG = "SettingsViewModel"

    private val _isDarkTheme = MutableLiveData(false)
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    init {
        Log.d(TAG, "ViewModel created")
    }

    fun toggleTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        Log.d(TAG, "Theme changed. Dark = $isDark")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}
