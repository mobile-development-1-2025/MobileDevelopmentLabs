package com.example.messenger_semester_7

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ProfileSettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val _name = MutableLiveData(application.getString(R.string.profile_name))
    val name: LiveData<String> = _name

    private val _status = MutableLiveData(application.getString(R.string.profile_status))
    val status: LiveData<String> = _status

    private val _isDarkTheme = MutableLiveData(ThemeManager.isDarkThemeEnabled(application))
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    init {
        Log.d(TAG, "ViewModel created")
    }

    fun saveProfile(newName: String, newStatus: String) {
        _name.value = newName
        _status.value = newStatus
        Log.d(TAG, "Profile saved: name=$newName, status=$newStatus")
    }

    fun setTheme(isDark: Boolean) {
        if (_isDarkTheme.value == isDark) {
            return
        }
        _isDarkTheme.value = isDark
        ThemeManager.setDarkThemeEnabled(getApplication(), isDark)
        ThemeManager.applyTheme(getApplication())
        Log.d(TAG, "Theme changed: $isDark")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }

    companion object {
        private const val TAG = "ProfileSettingsViewModel"
    }
}
