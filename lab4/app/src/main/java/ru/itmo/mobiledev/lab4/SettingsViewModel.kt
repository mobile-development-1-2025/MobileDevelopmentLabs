package ru.itmo.mobiledev.lab4

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val _darkThemeEnabled = MutableLiveData(false)
    val darkThemeEnabled: LiveData<Boolean> = _darkThemeEnabled
    private val _offlineEnabled = MutableLiveData(AppPreferences.isOffline(application))
    val offlineEnabled: LiveData<Boolean> = _offlineEnabled

    init {
        Log.d(TAG, "init")
    }

    fun setDarkThemeEnabled(enabled: Boolean) {
        if (_darkThemeEnabled.value != enabled) {
            _darkThemeEnabled.value = enabled
        }
    }

    fun setOfflineEnabled(enabled: Boolean) {
        if (_offlineEnabled.value != enabled) {
            _offlineEnabled.value = enabled
            AppPreferences.setOffline(getApplication(), enabled)
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
