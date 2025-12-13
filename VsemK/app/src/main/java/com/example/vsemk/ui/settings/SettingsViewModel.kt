package com.example.vsemk.ui.settings

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


    fun toggleTheme() {
        val newValue = !(_isDarkTheme.value ?: false)
        Log.d(TAG, "toggleTheme: $newValue")
        _isDarkTheme.value = newValue
    }


    fun setDarkTheme(isDark: Boolean) {
        Log.d(TAG, "setDarkTheme: $isDark")
        _isDarkTheme.value = isDark
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: ViewModel уничтожена")
    }

    init {
        Log.d(TAG, "init: ViewModel создана")
    }
}

