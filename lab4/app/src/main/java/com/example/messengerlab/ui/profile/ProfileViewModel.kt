package com.example.messengerlab.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val TAG = "ProfileViewModel"

    private val _name = MutableLiveData("Ника")
    val name: LiveData<String> = _name

    private val _email = MutableLiveData("nika@gmail.com")
    val email: LiveData<String> = _email

    private val _about = MutableLiveData("Статус: девопс")
    val about: LiveData<String> = _about

    init {
        Log.d(TAG, "ViewModel created")
    }

    fun updateName(value: String) { _name.value = value }
    fun updateEmail(value: String) { _email.value = value }
    fun updateAbout(value: String) { _about.value = value }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}
