package com.example.messenger

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "LifeCycleLog"
    private val PREFS_NAME = "profile_prefs"
    private val prefs = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val KEY_NAME = "name"
    private val KEY_USERNAME = "username"
    private val KEY_PHONE = "phoneNumber"
    private val KEY_DARK_MODE = "isDarkMode"

    private val _userProfile = MutableLiveData(loadProfile())
    val userProfile: LiveData<UserProfile> = _userProfile

    private val _isDarkMode = MutableLiveData(loadDarkMode())
    val isDarkMode: LiveData<Boolean> = _isDarkMode

    init {
        Log.d(TAG, "AppViewModel: init - Создан")
    }


    private fun loadProfile(): UserProfile {
        return UserProfile(
            name = prefs.getString(KEY_NAME, "Laura Palmer") ?: "Laura Palmer",
            username = prefs.getString(KEY_USERNAME, "meanwhile") ?: "meanwhile",
            phoneNumber = prefs.getString(KEY_PHONE, "+0 (000) 000-00-00") ?: "+0 (000) 000-00-00"
        )
    }

    fun saveProfile(name: String, username: String, phoneNumber: String) {
        _userProfile.value = UserProfile(name, username, phoneNumber)
        prefs.edit().apply {
            putString(KEY_NAME, name)
            putString(KEY_USERNAME, username)
            putString(KEY_PHONE, phoneNumber)
            apply()
        }
    }


    private fun loadDarkMode(): Boolean {
        return prefs.getBoolean(KEY_DARK_MODE, false)
    }

    fun setDarkMode(enabled: Boolean) {
        if (_isDarkMode.value != enabled) {
            _isDarkMode.value = enabled
            prefs.edit().putBoolean(KEY_DARK_MODE, enabled).apply()
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "AppViewModel: onCleared - Уничтожен")
    }
}