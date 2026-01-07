package com.example.messengerlab1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _darkTheme = MutableLiveData(false)
    val darkTheme: LiveData<Boolean> = _darkTheme

    init {
        Log.d("VM", "SettingsViewModel init")
    }

    fun setDarkTheme(enabled: Boolean) {
        _darkTheme.value = enabled
        Log.d("VM", "SettingsViewModel darkTheme=$enabled")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("VM", "SettingsViewModel onCleared")
    }
}