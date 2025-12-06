package com.example.messengerlab.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.messengerlab.databinding.FragmentProfileBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val vm: ProfileViewModel by viewModels()
    private val TAG = "ProfileFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated")

        vm.name.observe(viewLifecycleOwner) { name ->
            if (binding.etName.text.toString() != name) {
                binding.etName.setText(name)
            }
            binding.tvResult.text = "Имя: $name\nСтатус: ${vm.status.value ?: ""}"
        }

        vm.status.observe(viewLifecycleOwner) { status ->
            if (binding.etStatus.text.toString() != status) {
                binding.etStatus.setText(status)
            }
            binding.tvResult.text = "Имя: ${vm.name.value ?: ""}\nСтатус: $status"
        }

        binding.etName.doOnTextChanged { text, _, _, _ ->
            vm.updateName(text.toString())
        }

        binding.etStatus.doOnTextChanged { text, _, _, _ ->
            vm.updateStatus(text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
