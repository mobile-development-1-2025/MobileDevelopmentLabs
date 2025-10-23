package com.example.messenger_semester_7

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.messenger_semester_7.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SettingsFragment", "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("SettingsFragment", "onViewCreated")
        
        val isDarkTheme = ThemeManager.isDarkThemeEnabled(requireContext())
        binding.themeSwitch.isChecked = isDarkTheme
        
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d("SettingsFragment", "Theme switch changed: $isChecked")
            ThemeManager.setDarkThemeEnabled(requireContext(), isChecked)
            ThemeManager.applyTheme(requireContext())
            requireActivity().recreate()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("SettingsFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("SettingsFragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("SettingsFragment", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("SettingsFragment", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("SettingsFragment", "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SettingsFragment", "onDestroy")
    }
}
