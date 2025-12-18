package com.example.oktimessenger

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val TAG = "ProfileViewModel"

    init {
        Log.d(TAG, "ViewModel created")
    }

    override fun onCleared() {
        Log.d(TAG, "ViewModel cleared")
        super.onCleared()
    }

    private val _name = MutableLiveData<String>("Иван Иванов")
    val name: LiveData<String> get() = _name

    fun setName(newName: String) {
        if (_name.value != newName) {
            _name.value = newName
            Log.d(TAG, "Name updated: $newName")
        }
    }

    private val _status = MutableLiveData<String>("Онлайн")
    val status: LiveData<String> get() = _status

    fun setStatus(newStatus: String) {
        if (_status.value != newStatus) {
            _status.value = newStatus
            Log.d(TAG, "Status updated: $newStatus")
        }
    }

    private val _profileImageUri = MutableLiveData<Uri?>(null)
    val profileImageUri: LiveData<Uri?> get() = _profileImageUri

    fun setProfileImage(uri: Uri?) {
        _profileImageUri.value = uri
        Log.d(TAG, "Profile image uri set: $uri")
    }
}
