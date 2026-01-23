package com.m.cursproject.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.m.cursproject.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "ProfileFragment: onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "ProfileFragment: onCreateView()")
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "ProfileFragment: onViewCreated()")

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.userName.observe(viewLifecycleOwner) { name ->
            if (binding.editTextName.text.toString() != name) {
                binding.editTextName.setText(name)
            }
            binding.textDisplayName.text = name
        }

        viewModel.userStatus.observe(viewLifecycleOwner) { status ->
            if (binding.editTextStatus.text.toString() != status) {
                binding.editTextStatus.setText(status)
            }
            binding.textDisplayStatus.text = status
        }
    }

    private fun setupListeners() {
        binding.buttonSaveName.setOnClickListener {
            val newName = binding.editTextName.text.toString().trim()
            if (newName.isNotEmpty()) {
                viewModel.updateUserName(newName)
            }
        }

        binding.buttonSaveStatus.setOnClickListener {
            val newStatus = binding.editTextStatus.text.toString().trim()
            if (newStatus.isNotEmpty()) {
                viewModel.updateUserStatus(newStatus)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "ProfileFragment: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "ProfileFragment: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "ProfileFragment: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "ProfileFragment: onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "ProfileFragment: onDestroyView()")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "ProfileFragment: onDestroy()")
    }

    companion object {
        private const val TAG = "MessengerApp"
    }
}