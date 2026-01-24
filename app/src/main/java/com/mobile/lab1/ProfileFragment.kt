package com.mobile.lab1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.mobile.lab1.databinding.FragmentProfileBinding
import com.mobile.lab1.viewmodel.ProfileViewModel

class ProfileFragment : LoggingFragment("ProfileFragment") {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.userName.observe(viewLifecycleOwner) { name ->
            if (binding.etName.text.toString() != name) {
                binding.etName.setText(name)
            }
        }

        viewModel.userStatus.observe(viewLifecycleOwner) { status ->
            if (binding.etStatus.text.toString() != status) {
                binding.etStatus.setText(status)
            }
        }

        binding.etName.doOnTextChanged { text, _, _, _ ->
            viewModel.updateName(text?.toString().orEmpty())
        }

        binding.etStatus.doOnTextChanged { text, _, _, _ ->
            viewModel.updateStatus(text?.toString().orEmpty())
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}