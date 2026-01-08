package com.example.mymessenger.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mymessenger.databinding.FragmentProfileBinding
import com.example.mymessenger.viewModel.ProfileViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        Log.d("Lifecycle", "ProfileFragment onCreateView")
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userName.observe(viewLifecycleOwner) { name ->
            binding.tvCurrentName.text = "Имя: $name"
            binding.editName.setText(name)
        }

        viewModel.userStatus.observe(viewLifecycleOwner) { status ->
            binding.tvCurrentStatus.text = "Статус: $status"
            binding.editStatus.setText(status)
        }

        binding.btnSave.setOnClickListener {
            val newName = binding.editName.text.toString()
            val newStatus = binding.editStatus.text.toString()

            viewModel.updateUserName(newName)
            viewModel.updateUserStatus(newStatus)
        }

        binding.editName.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.updateUserName(binding.editName.text.toString())
            }
        }

        binding.editStatus.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.updateUserStatus(binding.editStatus.text.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Lifecycle", "ProfileFragment onDestroyView")
        _binding = null
    }
}