package com.example.lab1.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _name = MutableLiveData("Инна Гуторова")
    val name: LiveData<String> get() = _name

    private val _status = MutableLiveData("В сети")
    val status: LiveData<String> get() = _status

    private val _username = MutableLiveData("inngu")
    val username: LiveData<String> get() = _username

    private val _email = MutableLiveData("inna@example.com")
    val email: LiveData<String> get() = _email

    private val _phone = MutableLiveData("+1 234 567 89 00")
    val phone: LiveData<String> get() = _phone

    private val _bio = MutableLiveData(":)")
    val bio: LiveData<String> get() = _bio

    fun updateName(newName: String) {
        _name.value = newName
        Log.d("ProfileViewModel", "Name updated: $newName")
    }

    fun updateStatus(newStatus: String) {
        _status.value = newStatus
        Log.d("ProfileViewModel", "Status updated: $newStatus")
    }

    fun updateBio(newBio: String) {
        _bio.value = newBio
        Log.d("ProfileViewModel", "Bio updated: $newBio")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ProfileViewModel", "onCleared")
    }
}
