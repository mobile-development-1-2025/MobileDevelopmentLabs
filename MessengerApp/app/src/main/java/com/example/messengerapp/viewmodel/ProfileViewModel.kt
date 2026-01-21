package com.example.messengerapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    val userName = MutableLiveData<String>("Имя пользователя")
    val status = MutableLiveData<String>("Статус")

    init {
        Log.d("ProfileViewModel", "ViewModel created")
    }

    override fun onCleared() {
        Log.d("ProfileViewModel", "ViewModel cleared")
        super.onCleared()
    }
}
