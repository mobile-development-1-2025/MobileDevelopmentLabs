package com.example.messenger

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private val TAG = "LifeCycleLog"

    private lateinit var nameEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var saveButton: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "EditProfileFragment: onViewCreated()")

        nameEditText = view.findViewById(R.id.et_name)
        usernameEditText = view.findViewById(R.id.et_username)
        phoneEditText = view.findViewById(R.id.et_phone_number)
        val backButton = view.findViewById<View>(R.id.ll_back_button)
        saveButton = view.findViewById(R.id.tv_save_changes_button)

        nameEditText.setText(ProfileData.user.name)
        usernameEditText.setText(ProfileData.user.username)
        phoneEditText.setText(ProfileData.user.phoneNumber)

        backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        phoneEditText.addTextChangedListener(PhoneNumberFormattingTextWatcher(phoneEditText))

        saveButton.setOnClickListener {
            saveProfileChanges()
        }
    }

    private fun saveProfileChanges() {
        val newName = nameEditText.text.toString()
        val newUsername = usernameEditText.text.toString()
        val newPhoneNumber = phoneEditText.text.toString()

        ProfileData.user.name = newName
        ProfileData.user.username = newUsername
        ProfileData.user.phoneNumber = newPhoneNumber

        findNavController().navigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "EditProfileFragment: onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "EditProfileFragment: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "EditProfileFragment: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "EditProfileFragment: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "EditProfileFragment: onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "EditProfileFragment: onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "EditProfileFragment: onDestroy()")
    }
}