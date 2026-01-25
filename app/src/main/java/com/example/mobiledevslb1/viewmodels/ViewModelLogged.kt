package com.example.mobiledevslb1.viewmodels
import android.app.Application
import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.AndroidViewModel

open class ViewModelLogged(application: Application) : AndroidViewModel(application) {
    init {
        Log.d("ViewModel", "${this::class.simpleName} created")
    }

    override fun onCleared() {
        Log.d("ViewModel", "Called ${this::class.simpleName}.onCleared")
        super.onCleared()
    }
}