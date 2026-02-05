package com.example.messengerlab1.ui.settings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsScreenViewModel : ViewModel() {

    private val tag = "SettingsScreenVM"
    private val _isNightMode = MutableLiveData(false)
    val isNightMode: LiveData<Boolean> = _isNightMode

    init {
        Log.d(tag, "init: ViewModel настроек создана")
    }
    fun setNightMode(enabled: Boolean) {
        Log.d(tag, "setNightMode: $enabled")
        _isNightMode.value = enabled
    }

    override fun onCleared() {
        Log.d(tag, "onCleared: ViewModel настроек будет уничтожена")
        super.onCleared()
    }
}
