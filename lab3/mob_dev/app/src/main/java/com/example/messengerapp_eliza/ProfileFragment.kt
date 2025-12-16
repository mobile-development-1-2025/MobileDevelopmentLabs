package com.example.messengerapp_eliza

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.messengerapp_eliza.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ProfileFragment", "onCreate called")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        viewModel.userName.observe(viewLifecycleOwner, Observer { name ->
            binding.nameEdit.setText(name)
        })

        viewModel.userStatus.observe(viewLifecycleOwner, Observer { status ->
            binding.statusEdit.setText(status)
        })

        binding.nameEdit.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.updateUserName(binding.nameEdit.text.toString())
            }
        }

        binding.statusEdit.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.updateUserStatus(binding.statusEdit.text.toString())
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ProfileFragment", "onDestroy called")
    }
}