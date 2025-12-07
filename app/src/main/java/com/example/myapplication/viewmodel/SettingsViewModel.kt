package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    init {
        Log.i("ViewModel", "SettingsViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ViewModel", "SettingsViewModel cleared")
    }

    private val _darkMode = MutableLiveData<Boolean>(false)
    val darkMode: LiveData<Boolean> get() = _darkMode
    fun setDarkMode(enabled: Boolean) {
        _darkMode.value = enabled
    }
}