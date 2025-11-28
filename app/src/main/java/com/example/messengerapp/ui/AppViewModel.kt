package com.example.messengerapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {

    private val _name = MutableLiveData("glafira")
    val name: LiveData<String> = _name

    private val _status = MutableLiveData("online")
    val status: LiveData<String> = _status

    private val _isDark = MutableLiveData(false)
    val isDark: LiveData<Boolean> = _isDark

    init {
        Log.d("VM", "AppViewModel init")
    }

    fun updateName(newName: String) { _name.value = newName }
    fun updateStatus(newStatus: String) { _status.value = newStatus }
    fun setDarkMode(enabled: Boolean) { _isDark.value = enabled }

    override fun onCleared() {
        Log.d("VM", "AppViewModel onCleared()")
        super.onCleared()
    }
}