package com.example.messager.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _inputText = MutableLiveData<String>()
    val inputText: MutableLiveData<String> = _inputText

    fun updateText(text: String) {
        _inputText.value = text
    }
}