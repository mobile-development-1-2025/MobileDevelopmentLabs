package com.example.lab1

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

data class ProfileState(
    val name: String = "Иван Иванов",
    val email: String = "ivan@example.com"
)

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val _profileState = MutableLiveData(ProfileState())
    val profileState: LiveData<ProfileState> = _profileState

    private val _isDarkTheme = MutableLiveData(ThemeManager.isDarkThemeEnabled(application))
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    init {
        Log.d(TAG, "AppViewModel created")
        Log.d(TAG, "Initial ProfileState: ${_profileState.value}")
        Log.d(TAG, "Initial DarkTheme: ${_isDarkTheme.value}")
    }

    fun updateName(newName: String) {
        Log.d(TAG, "updateName called: $newName")
        val old = _profileState.value
        _profileState.value = old?.copy(name = newName)

        Log.d(TAG, "ProfileState updated: ${_profileState.value}")
    }

    fun updateEmail(newEmail: String) {
        Log.d(TAG, "updateEmail called: $newEmail")
        val old = _profileState.value
        _profileState.value = old?.copy(email = newEmail)

        Log.d(TAG, "ProfileState updated: ${_profileState.value}")
    }

    fun setDarkThemeEnabled(enabled: Boolean) {
        Log.d(TAG, "setDarkThemeEnabled called: enabled = $enabled")

        if (_isDarkTheme.value == enabled) {
            Log.d(TAG, "DarkTheme unchanged, skipping update")
            return
        }

        _isDarkTheme.value = enabled
        Log.d(TAG, "DarkTheme updated: ${_isDarkTheme.value}")

        ThemeManager.saveAndApply(getApplication(), enabled)
        Log.d(TAG, "ThemeManager.saveAndApply executed")
    }

    override fun onCleared() {
        Log.d(TAG, "AppViewModel cleared (ViewModel is being destroyed)")
        super.onCleared()
    }

    companion object {
        private const val TAG = "AppViewModel"
    }
}
