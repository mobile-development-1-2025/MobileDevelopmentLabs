package ru.itmo.mobiledev.lab2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _darkThemeEnabled = MutableLiveData(false)
    val darkThemeEnabled: LiveData<Boolean> = _darkThemeEnabled

    init {
        Log.d(TAG, "init")
    }

    fun setDarkThemeEnabled(enabled: Boolean) {
        if (_darkThemeEnabled.value != enabled) {
            _darkThemeEnabled.value = enabled
        }
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared")
        super.onCleared()
    }

    companion object {
        private const val TAG = "SettingsViewModel"
    }
}
