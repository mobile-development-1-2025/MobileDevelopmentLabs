package com.waycooler.messengermih.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _name = MutableLiveData("Имя пользователя")
    val name: LiveData<String> = _name

    private val _status = MutableLiveData("Мой статус")
    val status: LiveData<String> = _status

    init {
        Log.d("ProfileViewModel", "init")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ProfileViewModel", "onCleared")
    }

    fun updateName(value: String) {
        _name.value = value
    }

    fun updateStatus(value: String) {
        _status.value = value
    }
}
