package ru.itmo.mobiledev.lab3

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _name = MutableLiveData("Тимур")
    val name: LiveData<String> = _name

    private val _status = MutableLiveData("онлайн")
    val status: LiveData<String> = _status

    init {
        Log.d(TAG, "init")
    }

    fun updateName(value: String) {
        if (_name.value != value) {
            _name.value = value
        }
    }

    fun updateStatus(value: String) {
        if (_status.value != value) {
            _status.value = value
        }
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared")
        super.onCleared()
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}
