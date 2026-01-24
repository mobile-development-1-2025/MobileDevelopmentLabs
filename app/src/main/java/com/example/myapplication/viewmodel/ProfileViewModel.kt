package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    init {
        Log.i("ViewModel", "ProfileViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ViewModel", "ProfileViewModel cleared")
    }

    private val _name = MutableLiveData<String>("Имя")
    private val _status = MutableLiveData<String>("Статус")

    val name: LiveData<String> get() = _name
    val status: LiveData<String> get() = _status

    fun updateName(newName: String) {
        _name.value = newName
    }

    fun updateStatus(newStatus: String) {
        _status.value = newStatus
    }
}