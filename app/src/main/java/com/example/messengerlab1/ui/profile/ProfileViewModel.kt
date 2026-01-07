package com.example.messengerlab1.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _editMode = MutableLiveData(false)
    val editMode: LiveData<Boolean> = _editMode

    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    private val _status = MutableLiveData("")
    val status: LiveData<String> = _status

    private val _username = MutableLiveData("")
    val username: LiveData<String> = _username

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _phone = MutableLiveData("")
    val phone: LiveData<String> = _phone

    private val _bio = MutableLiveData("")
    val bio: LiveData<String> = _bio

    init {
        Log.d("VM", "ProfileViewModel init")
    }

    fun toggleEditMode() {
        _editMode.value = !(_editMode.value ?: false)
        Log.d("VM", "ProfileViewModel editMode=${_editMode.value}")
    }

    fun setName(v: String) { _name.value = v }
    fun setStatus(v: String) { _status.value = v }
    fun setUsername(v: String) { _username.value = v }
    fun setEmail(v: String) { _email.value = v }
    fun setPhone(v: String) { _phone.value = v }
    fun setBio(v: String) { _bio.value = v }

    override fun onCleared() {
        super.onCleared()
        Log.d("VM", "ProfileViewModel onCleared")
    }
}