package com.example.messengerlab.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _name = MutableLiveData("")
    val name: LiveData<String> get() = _name

    private val _status = MutableLiveData("")
    val status: LiveData<String> get() = _status

    fun updateName(new: String) {
        _name.value = new
    }

    fun updateStatus(new: String) {
        _status.value = new
    }

    init {
        Log.d("ProfileVM", "Создан ProfileViewModel (один раз за всё время)")
    }

    override fun onCleared() {
        Log.d("ProfileVM", "ProfileViewModel уничтожен (когда закрываешь приложение)")
        super.onCleared()
    }
}