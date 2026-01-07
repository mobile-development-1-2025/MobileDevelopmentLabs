package com.example.messengerlab1.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.messengerlab1.ui.profile.ProfileViewModel
import com.example.messengerlab1.databinding.FragmentProfileBinding
import com.google.android.material.textfield.TextInputEditText

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val vm: ProfileViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // VM -> UI
        vm.name.observe(viewLifecycleOwner) { setIfDifferent(binding.etName, it) }
        vm.status.observe(viewLifecycleOwner) { setIfDifferent(binding.etStatus, it) }
        vm.username.observe(viewLifecycleOwner) { setIfDifferent(binding.etUsername, it) }
        vm.email.observe(viewLifecycleOwner) { setIfDifferent(binding.etEmail, it) }
        vm.phone.observe(viewLifecycleOwner) { setIfDifferent(binding.etPhone, it) }
        vm.bio.observe(viewLifecycleOwner) { setIfDifferent(binding.etBio, it) }

        vm.editMode.observe(viewLifecycleOwner) { edit ->
            setEditable(edit)
            binding.btnEditSave.text = if (edit) "Сохранить" else "Редактировать"
        }
        binding.etName.addTextChangedListener { vm.setName(it?.toString().orEmpty()) }
        binding.etStatus.addTextChangedListener { vm.setStatus(it?.toString().orEmpty()) }
        binding.etUsername.addTextChangedListener { vm.setUsername(it?.toString().orEmpty()) }
        binding.etEmail.addTextChangedListener { vm.setEmail(it?.toString().orEmpty()) }
        binding.etPhone.addTextChangedListener { vm.setPhone(it?.toString().orEmpty()) }
        binding.etBio.addTextChangedListener { vm.setBio(it?.toString().orEmpty()) }

        binding.btnEditSave.setOnClickListener {
            vm.toggleEditMode()
        }
    }

    private fun setEditable(enabled: Boolean) = with(binding) {
        etName.isEnabled = enabled
        etStatus.isEnabled = enabled
        etUsername.isEnabled = enabled
        etEmail.isEnabled = enabled
        etPhone.isEnabled = enabled
        etBio.isEnabled = enabled
    }

    private fun setIfDifferent(editText: TextInputEditText, value: String) {
        val current = editText.text?.toString() ?: ""
        if (current != value) editText.setText(value)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}