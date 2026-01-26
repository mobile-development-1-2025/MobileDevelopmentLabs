package com.example.lab1.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.lab1.databinding.FragmentProfileBinding
import com.example.lab1.viewmodel.ProfileViewModel
import kotlin.math.min

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val TAG = "ProfileFragment"

    private val viewModel: ProfileViewModel by activityViewModels()

    private var nameWatcher: TextWatcher? = null
    private var statusWatcher: TextWatcher? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")

        viewModel.name.observe(viewLifecycleOwner) { name ->
            val current = binding.editName.text.toString()
            if (current != name && !binding.editName.isFocused) {
                val pos = binding.editName.selectionStart.coerceAtLeast(0)
                nameWatcher?.let { binding.editName.removeTextChangedListener(it) }
                binding.editName.setText(name)
                binding.editName.setSelection(min(name.length, pos))
                nameWatcher?.let { binding.editName.addTextChangedListener(it) }
            }
        }

        viewModel.status.observe(viewLifecycleOwner) { status ->
            val current = binding.editStatus.text.toString()
            if (current != status && !binding.editStatus.isFocused) {
                val pos = binding.editStatus.selectionStart.coerceAtLeast(0)
                statusWatcher?.let { binding.editStatus.removeTextChangedListener(it) }
                binding.editStatus.setText(status)
                binding.editStatus.setSelection(min(status.length, pos))
                statusWatcher?.let { binding.editStatus.addTextChangedListener(it) }
            }
        }

        nameWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val newVal = s?.toString().orEmpty()
                if (viewModel.name.value != newVal) {
                    viewModel.updateName(newVal)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        statusWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val newVal = s?.toString().orEmpty()
                if (viewModel.status.value != newVal) {
                    viewModel.updateStatus(newVal)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        binding.editName.addTextChangedListener(nameWatcher)
        binding.editStatus.addTextChangedListener(statusWatcher)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        nameWatcher?.let { binding.editName.removeTextChangedListener(it) }
        statusWatcher?.let { binding.editStatus.removeTextChangedListener(it) }
        _binding = null
    }
}