package com.example.oktimessenger

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.oktimessenger.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val TAG = "SettingsFragment"

    private val settingsViewModel: SettingsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)

        val isDark = prefs.getBoolean("dark_mode", false)
        settingsViewModel.setTheme(isDark)

        settingsViewModel.isDarkTheme.observe(viewLifecycleOwner) { isDarkMode ->
            Log.d(TAG, "LiveData update: theme = $isDarkMode")

            binding.switchTheme.setOnCheckedChangeListener(null)

            binding.switchTheme.isChecked = isDarkMode

            binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
                settingsViewModel.setTheme(isChecked)
                prefs.edit().putBoolean("dark_mode", isChecked).apply()
                Log.d(TAG, "User switched theme: $isChecked")
            }

            AppCompatDelegate.setDefaultNightMode(
                if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
