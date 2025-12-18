package com.example.oktimessenger

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val TAG = "SettingsViewModel"

    private val _isDarkTheme = MutableLiveData(false)
    val isDarkTheme: LiveData<Boolean> get() = _isDarkTheme

    fun setTheme(isDark: Boolean) {
        Log.d(TAG, "setTheme: $isDark")
        _isDarkTheme.value = isDark
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}
