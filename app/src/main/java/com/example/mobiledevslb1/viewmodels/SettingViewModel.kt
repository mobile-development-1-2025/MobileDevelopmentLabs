package com.example.mobiledevslb1.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData

class SettingViewModel: ViewModelLogged() {
    private val _isDarkTheme = MutableLiveData(false)
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme
    fun setIsDarkTheme(value: Boolean) {
        _isDarkTheme.value = value
        Log.d("ViewModel", "Called SettingViewFragment.isDarkTheme setter")
    }
    fun changeIsDarkTheme() {
        _isDarkTheme.value = !(_isDarkTheme.value ?: false)
        Log.d("ViewModel", "Called SettingViewFragment.isDarkTheme setter")
    }
}