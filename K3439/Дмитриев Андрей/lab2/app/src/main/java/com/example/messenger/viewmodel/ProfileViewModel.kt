package com.example.messenger.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel для экрана Профиля
 * Хранит состояние профиля пользователя и переживает повороты экрана
 */
class ProfileViewModel : ViewModel() {
    
    private val TAG = "ProfileViewModel"
    
    // LiveData для имени пользователя
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName
    
    // LiveData для статуса пользователя
    private val _userStatus = MutableLiveData<String>()
    val userStatus: LiveData<String> = _userStatus
    
    init {
        Log.d(TAG, "init: ProfileViewModel создан")
        // Устанавливаем значения по умолчанию
        _userName.value = "Дмитриев Андрей"
        _userStatus.value = "Android разработчик. Люблю создавать крутые приложения!"
    }
    
    /**
     * Обновление имени пользователя
     */
    fun updateUserName(name: String) {
        Log.d(TAG, "updateUserName: Имя обновлено на '$name'")
        _userName.value = name
    }
    
    /**
     * Обновление статуса пользователя
     */
    fun updateUserStatus(status: String) {
        Log.d(TAG, "updateUserStatus: Статус обновлен на '$status'")
        _userStatus.value = status
    }
    
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: ProfileViewModel уничтожен")
    }
}
