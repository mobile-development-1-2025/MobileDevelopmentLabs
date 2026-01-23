package com.m.cursproject.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.m.cursproject.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "SettingsFragment: onCreate()")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "SettingsFragment: onCreateView()")
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "SettingsFragment: onViewCreated()")

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.isDarkTheme.observe(viewLifecycleOwner) { isDark ->
            binding.switchTheme.isChecked = isDark
            applyTheme(isDark)
        }
    }

    private fun setupListeners() {
        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setTheme(isChecked)
        }
    }

    private fun applyTheme(isDark: Boolean) {
        val mode = if (isDark) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "SettingsFragment: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "SettingsFragment: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "SettingsFragment: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "SettingsFragment: onStop()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "SettingsFragment: onDestroyView()")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "SettingsFragment: onDestroy()")
    }

    companion object {
        private const val TAG = "MessengerApp"
    }
}