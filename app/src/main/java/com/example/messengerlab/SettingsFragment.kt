package com.example.messengerlab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.messengerlab.databinding.FragmentSettingsBinding

class SettingsFragment: Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val TAG = "SettingsFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        val prefs = requireActivity().getSharedPreferences("app_prefs", 0)
        val dark = prefs.getBoolean("dark_theme", false)
        binding.switchTheme.isChecked = dark

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_theme", isChecked).apply()
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
        _binding = null
    }
}
