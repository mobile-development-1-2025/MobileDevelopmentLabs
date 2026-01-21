package com.example.messengerapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    val isDarkTheme = MutableLiveData<Boolean>(false)

    init {
        Log.d("SettingsViewModel", "ViewModel created")
    }

    override fun onCleared() {
        Log.d("SettingsViewModel", "ViewModel cleared")
        super.onCleared()
    }
}
