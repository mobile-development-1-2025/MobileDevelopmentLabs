package com.example.messenger.viewmodel

import android.util.Log
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

    init {
        Log.d(TAG, "ViewModel created")
    }

    fun setDarkTheme(enabled: Boolean) {
        _isDarkTheme.value = enabled
        Log.d(TAG, "Dark theme updated: $enabled")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}
