

package com.example.mobiledevslb1.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData

class ProfileViewModel(application: Application): ViewModelLogged(application) {
    private val _name = MutableLiveData("Никнейм")
    val name: LiveData<String> = _name
    fun setName(value: String) {
        _name.value = value
        Log.d("ViewModel", "Called ProfileViewModel.name setter")
    }

    private val _age = MutableLiveData<Int?>(0)
    val age: LiveData<Int?> = _age
    fun setAge(value: Int?) {
        if (value == null) _age.value = 0
        else _age.value = value
        Log.d("ViewModel", "Called ProfileViewModel.age setter")
    }
}