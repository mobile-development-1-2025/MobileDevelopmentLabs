package com.example.mymessenger.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymessenger.data.UserPrefs

class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    private val TAG = "ProfileViewModel"

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    init {
        Log.d(TAG, "ViewModel created")

        val context = getApplication<Application>().applicationContext
        _name.value = UserPrefs.loadName(context)
        _status.value = UserPrefs.loadStatus(context)

        Log.d(TAG, "Loaded from prefs: name=${_name.value}, status=${_status.value}")
    }

    fun updateName(newName: String) {
        _name.value = newName
        val context = getApplication<Application>().applicationContext
        UserPrefs.saveName(context, newName)
        Log.d(TAG, "Name updated & saved: $newName")
    }

    fun updateStatus(newStatus: String) {
        _status.value = newStatus
        val context = getApplication<Application>().applicationContext
        UserPrefs.saveStatus(context, newStatus)
        Log.d(TAG, "Status updated & saved: $newStatus")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}
