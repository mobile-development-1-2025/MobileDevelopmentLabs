package com.example.messengerlab1.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileScreenViewModel : ViewModel() {

    private val tag = "ProfileScreenVM"
    private val _nickname = MutableLiveData("Дарья")
    val nickname: LiveData<String> = _nickname
    private val _mood = MutableLiveData("Пытаюсь разобраться с MVVM")
    val mood: LiveData<String> = _mood

    init {
        Log.d(tag, "init: ViewModel профиля создана")
    }

    fun onNicknameChanged(newName: String) {
        Log.d(tag, "onNicknameChanged: $newName")
        _nickname.value = newName
    }

    fun onMoodChanged(newMood: String) {
        Log.d(tag, "onMoodChanged: $newMood")
        _mood.value = newMood
    }

    override fun onCleared() {
        Log.d(tag, "onCleared: ViewModel профиля будет уничтожена")
        super.onCleared()
    }
}
