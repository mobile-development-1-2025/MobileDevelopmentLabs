package com.example.mymessenger.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mymessenger.databinding.FragmentSettingsBinding
import com.example.mymessenger.viewModel.SettingsViewModel

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val settingsViewModel: SettingsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        Log.d("Lifecycle", "com.example.mymessenger.fragments.SettingsFragment onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.themeSwitch.isChecked = settingsViewModel.isDarkTheme()

        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.setDarkTheme(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Lifecycle", "com.example.mymessenger.fragments.SettingsFragment onDestroyView")
        _binding = null
    }
}