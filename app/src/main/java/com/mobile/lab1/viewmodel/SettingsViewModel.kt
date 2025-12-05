package com.mobile.lab1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    companion object {
        private const val TAG = "SettingsViewModel"
    }

    private val _isDarkTheme = MutableLiveData(false)
    val isDarkTheme: LiveData<Boolean> get() = _isDarkTheme

    init {
        Log.d(TAG, "init: SettingsViewModel создан")
    }

    fun setDarkTheme(enabled: Boolean) {
        Log.d(TAG, "setDarkTheme: $enabled")
        _isDarkTheme.value = enabled
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: SettingsViewModel уничтожен")
        super.onCleared()
    }
}