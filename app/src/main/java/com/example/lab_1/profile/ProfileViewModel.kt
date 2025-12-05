package com.example.lab_1.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _name = MutableLiveData("Maria Bakhareva")
    val name: LiveData<String> = _name

    private val _status = MutableLiveData("Online")
    val status: LiveData<String> = _status

    init {
        Log.i("ProfileViewModel", "init")
    }

    fun setName(value: String) {
        if (value != _name.value) {
            _name.value = value
            Log.i("ProfileViewModel", "setName: $value")
        }
    }

    fun setStatus(value: String) {
        if (value != _status.value) {
            _status.value = value
            Log.i("ProfileViewModel", "setStatus: $value")
        }
    }

    override fun onCleared() {
        Log.i("ProfileViewModel", "onCleared")
        super.onCleared()
    }
}
