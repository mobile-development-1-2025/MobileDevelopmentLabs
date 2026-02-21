package com.example.messengerlab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.messengerlab.databinding.FragmentProfileBinding
import com.example.messengerlab.ui.profile.ProfileViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val TAG = "ProfileFragment"
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        viewModel.name.observe(viewLifecycleOwner) { binding.tvName.text = it }
        viewModel.email.observe(viewLifecycleOwner) { binding.tvEmail.text = it }
        viewModel.about.observe(viewLifecycleOwner) { binding.tvAbout.text = it }

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val about = binding.etAbout.text.toString().trim()
            if (name.isNotEmpty()) viewModel.updateName(name)
            if (email.isNotEmpty()) viewModel.updateEmail(email)
            if (about.isNotEmpty()) viewModel.updateAbout(about)
            binding.etName.text.clear()
            binding.etEmail.text.clear()
            binding.etAbout.text.clear()
        }
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop") }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
        _binding = null
    }

    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy") }
}
