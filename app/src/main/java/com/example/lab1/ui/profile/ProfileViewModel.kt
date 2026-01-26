package com.example.lab1.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val tag = "ProfileViewModel"

    // Внутренние изменяемые поля
    private val _name = MutableLiveData<String>("Firsov Ilia")
    private val _status = MutableLiveData<String>("Studying kotlin")

    // Публичные LiveData только на чтение
    val name: LiveData<String> = _name
    val status: LiveData<String> = _status

    init {
        Log.d(tag, "init: ViewModel создана")
    }

    fun updateName(newName: String) {
        Log.d(tag, "updateName: $newName")
        _name.value = newName
    }

    fun updateStatus(newStatus: String) {
        Log.d(tag, "updateStatus: $newStatus")
        _status.value = newStatus
    }

    override fun onCleared() {
        Log.d(tag, "onCleared: ViewModel будет уничтожена")
        super.onCleared()
    }
}
