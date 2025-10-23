package com.example.messengerlab1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.messengerlab1.databinding.FragmentProfileBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var editMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "ProfileFragment onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            ProfileStore.flow(requireContext()).collectLatest { p ->
                if (!editMode) {
                    binding.etName.setText(p.name)
                    binding.etUsername.setText(p.username)
                    binding.etEmail.setText(p.email)
                    binding.etPhone.setText(p.phone)
                    binding.etBio.setText(p.bio)
                }
            }
        }

        binding.btnEditSave.setOnClickListener {
            if (!editMode) {
                setEditable(true)
                binding.btnEditSave.text = "Сохранить"
                editMode = true
            } else {
                val profile = ProfileStore.Profile(
                    name = binding.etName.text?.toString().orEmpty(),
                    username = binding.etUsername.text?.toString().orEmpty(),
                    email = binding.etEmail.text?.toString().orEmpty(),
                    phone = binding.etPhone.text?.toString().orEmpty(),
                    bio = binding.etBio.text?.toString().orEmpty()
                )
                viewLifecycleOwner.lifecycleScope.launch {
                    ProfileStore.save(requireContext(), profile)
                    Snackbar.make(requireView(), "Сохранено", Snackbar.LENGTH_SHORT).show()
                }
                setEditable(false)
                binding.btnEditSave.text = "Редактировать"
                editMode = false
            }
        }

        setEditable(false)
    }

    private fun setEditable(enabled: Boolean) = with(binding) {
        etName.isEnabled = enabled
        etUsername.isEnabled = enabled
        etEmail.isEnabled = enabled
        etPhone.isEnabled = enabled
        etBio.isEnabled = enabled
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}