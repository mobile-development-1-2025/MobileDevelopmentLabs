package com.example.messenger

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.activityViewModels

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private val TAG = "LifeCycleLog"

    private val viewModel: AppViewModel by activityViewModels()

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

        val currentProfile = viewModel.userProfile.value
        currentProfile?.let {
            nameEditText.setText(it.name)
            usernameEditText.setText(it.username)
            phoneEditText.setText(it.phoneNumber)
        }

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

        viewModel.saveProfile(newName, newUsername, newPhoneNumber)

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