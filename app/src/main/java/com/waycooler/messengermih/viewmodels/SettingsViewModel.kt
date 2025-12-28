package com.waycooler.messengermih.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.waycooler.messengermih.data.SettingsDataStore
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val _dataStore = SettingsDataStore(application)
    private val _darkTheme = MutableLiveData(false)
    val darkTheme: LiveData<Boolean> = _darkTheme

    init {
        Log.d("SettingsViewModel", "init")

        viewModelScope.launch {
            _dataStore.darkThemeFlow.collect { savedValue ->
                _darkTheme.postValue(savedValue)
                Log.d("SettingsViewModel", "Loaded theme = $savedValue")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("SettingsViewModel", "onCleared")
    }

    fun setDarkTheme(value: Boolean) {
        _darkTheme.value = value
        Log.d("SettingsViewModel", "Theme changed to $value")

        viewModelScope.launch {
            _dataStore.saveDarkTheme(value)
        }
    }
}
