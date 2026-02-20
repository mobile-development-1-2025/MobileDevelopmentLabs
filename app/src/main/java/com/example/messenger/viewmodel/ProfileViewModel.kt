package com.example.messenger.viewmodel

import android.util.Log
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.example.messenger.data.dto.UserData

class ProfileViewModel(context: Context): ViewModel() {
    companion object {
        private const val tag: String = "ProfileVM"
        private const val KEY_PREFS: String = "app_profile"
        private const val KEY_PROFILE = "user_profile"
    }

    private val gson = Gson()
    private val prefs: SharedPreferences =
        context.getSharedPreferences(KEY_PREFS, Context.MODE_PRIVATE)

    private val _profile = MutableLiveData<UserData>()
    val profile: LiveData<UserData> = _profile

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    init {
        loadProfile()
        Log.d(tag, "init ProfileViewModel;")
    }

    private fun saveProfile(profile: UserData) {
        try {
            val json = gson.toJson(profile)
            prefs.edit().putString("user_profile", json).apply()
            _profile.value = profile
            _toastMessage.value = "Profile saved"
        } catch (e: Exception) {
            _toastMessage.value = "Error saving profile"
        }
    }

    private fun loadProfile() {
        try {
            val json = prefs.getString(KEY_PROFILE, null)
            Log.d(tag, "Загрузка профиля: $json")

            if (!json.isNullOrEmpty()) {
                val loadedProfile = gson.fromJson(json, UserData::class.java)
                _profile.value = loadedProfile
            } else {
                _profile.value = UserData()
                Log.d(tag, "Профиль не найден, создан пустой")
            }
        } catch (e: Exception) {
            Log.e(tag, "Ошибка загрузки профиля: ${e.message}")
            _profile.value = UserData()
            _toastMessage.value = "Error loading profile"
        }
    }

    fun updateUserName(name: String) {
        val current = _profile.value ?: UserData()
        saveProfile(current.copy(name = name))
    }

    fun updateUserEmail(email: String) {
        val current = _profile.value ?: UserData()
        saveProfile(current.copy(email = email))
    }

    fun updateUserPhone(phone: String) {
        val current = _profile.value ?: UserData()
        saveProfile(current.copy(phone = phone))
    }

    fun updateUserBio(bio: String) {
        val current = _profile.value ?: UserData()
        saveProfile(current.copy(bio = bio))
    }
}
