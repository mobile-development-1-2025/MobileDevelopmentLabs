package com.example.messenger.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel для экрана Настроек
 * Хранит состояние настроек приложения и переживает повороты экрана
 */
class SettingsViewModel : ViewModel() {
    
    private val TAG = "SettingsViewModel"
    
    // LiveData для состояния темной темы
    private val _isDarkTheme = MutableLiveData<Boolean>()
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme
    
    init {
        Log.d(TAG, "init: SettingsViewModel создан")
        // Значение по умолчанию - светлая тема
        _isDarkTheme.value = false
    }
    
    /**
     * Переключение темы
     */
    fun setDarkTheme(isDark: Boolean) {
        Log.d(TAG, "setDarkTheme: Тема изменена на ${if (isDark) "Тёмную" else "Светлую"}")
        _isDarkTheme.value = isDark
    }
    
    /**
     * Инициализация начального состояния темы из SharedPreferences
     */
    fun initTheme(isDark: Boolean) {
        Log.d(TAG, "initTheme: Инициализация темы - ${if (isDark) "Тёмная" else "Светлая"}")
        _isDarkTheme.value = isDark
    }
    
    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: SettingsViewModel уничтожен")
    }
}
