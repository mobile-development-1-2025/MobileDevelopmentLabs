package com.example.lab1misha.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class ProfileStatus(val displayName: String) {
    ONLINE("В сети"),
    RECENTLY("Был недавно"),
    OFFLINE("Не в сети");

    companion object {
        fun fromDisplayName(name: String) = values().firstOrNull { it.displayName == name } ?: ONLINE
    }
}

class ProfileViewModel : ViewModel() {

    private val TAG = "ProfileViewModel"

    private val _userName = MutableLiveData<String>("Чернышев Михаил")
    val userName: LiveData<String> = _userName

    private val _userStatus = MutableLiveData<String>("В сети")
    val userStatus: LiveData<String> = _userStatus

    private val _userStatusEnum = MutableLiveData<ProfileStatus>(ProfileStatus.ONLINE)
    val userStatusEnum: LiveData<ProfileStatus> = _userStatusEnum

    init {
        Log.d(TAG, "ViewModel создана")
    }

    fun updateUserName(name: String) {
        _userName.value = name
        Log.d(TAG, "Имя пользователя обновлено: $name")
    }

    fun updateUserStatus(status: String) {
        _userStatus.value = status
        val enumStatus = ProfileStatus.fromDisplayName(status)
        _userStatusEnum.value = enumStatus
        Log.d(TAG, "Статус пользователя обновлен: $status")
    }

    fun updateUserStatus(enumStatus: ProfileStatus) {
        _userStatusEnum.value = enumStatus
        _userStatus.value = enumStatus.displayName
        Log.d(TAG, "Статус пользователя обновлен через enum: ${enumStatus.displayName}")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel уничтожена")
    }
}
