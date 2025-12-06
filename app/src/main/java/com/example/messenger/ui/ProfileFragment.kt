package com.example.messenger.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.messenger.databinding.FragmentProfileBinding
import com.example.messenger.viewmodel.ProfileViewModel
import com.example.messenger.data.UserData

class ProfileFragment: Fragment() {
    private val tag: String = "Profile"
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate: Fragment создан")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(tag, "onCreateView: Создание View для Fragment")
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tag, "onViewCreated: View создан")

        viewModel = ProfileViewModel(requireContext())

        setupObservers()
        setupForm()
        checkCurrentProfile()
    }

    private fun checkCurrentProfile() {
        val currentProfile = viewModel.profile.value
        Log.d(tag, "Текущий профиль из ViewModel: $currentProfile")

        if (currentProfile != null && currentProfile.name.isNotEmpty()) {
            populateForm(currentProfile)
        } else {
            Log.d(tag, "Профиль пустой или не загружен")
        }
    }

    private fun populateForm(profile: UserData) {
        binding.editName.setText(profile.name)
        binding.editEmail.setText(profile.email)
//        binding.editPhone.setText(profile.phone)
//        binding.editBio.setText(profile.bio)
    }

    private fun setupObservers() {
        viewModel.profile.observe(viewLifecycleOwner) { profile ->
            if (profile.name.isNotEmpty()) {
                populateForm(profile)
            }
        }

        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            if (message.isNotEmpty()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveProfile() {
        val name = binding.editName.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
//        val phone = binding.editPhone.text.toString().trim()
//        val bio = binding.editBio.text.toString().trim()

        viewModel.updateUserName(name)
        viewModel.updateUserEmail(email)
    }

    private fun setupForm() {
        binding.editProfileButton.setOnClickListener {
            saveProfile()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart: Fragment запущен")
    }
}