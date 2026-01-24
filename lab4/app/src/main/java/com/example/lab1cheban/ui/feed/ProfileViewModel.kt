package com.example.messenger.ui.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProfileData(
    val name: String = "Илья Чебан",
    val status: String = "В сети",
    val email: String = "ilyacheban2004@mail.ru",
    val phone: String = "+7-911-123-33-30"
)

class ProfileViewModel : ViewModel() {
    private val _profileData = MutableStateFlow(ProfileData())
    val profileData: StateFlow<ProfileData> = _profileData.asStateFlow()

    fun updateName(newName: String) {
        _profileData.value = _profileData.value.copy(name = newName)
    }

    fun updateStatus(newStatus: String) {
        _profileData.value = _profileData.value.copy(status = newStatus)
    }
}