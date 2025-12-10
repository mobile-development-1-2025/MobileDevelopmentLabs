package com.example.mobiledevslb1.viewmodels
import androidx.lifecycle.ViewModel
import android.util.Log

open class ViewModelLogged: ViewModel() {
    init {
        Log.d("ViewModel", "${this::class.simpleName} created")
    }

    override fun onCleared() {
        Log.d("ViewModel", "Called ${this::class.simpleName}.onCleared")
        super.onCleared()
    }
}