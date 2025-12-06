package com.example.lab1.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.lab1.databinding.FragmentSettingsBinding
import com.example.lab1.viewmodel.ProfileSettingsViewModel

class SettingsFragment : Fragment() {

    private val TAG = "SettingsFragment"

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileSettingsViewModel by activityViewModels()

    private var checkedChangeListener: ((android.widget.CompoundButton, Boolean) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        Log.d(TAG, "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.isDarkTheme.observe(viewLifecycleOwner) { isDark ->
            if (binding.switchTheme.isChecked != isDark) {
                binding.switchTheme.setOnCheckedChangeListener(null)
                binding.switchTheme.isChecked = isDark
                binding.switchTheme.setOnCheckedChangeListener { _, checked ->
                    viewModel.updateTheme(checked)
                }
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateTheme(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
