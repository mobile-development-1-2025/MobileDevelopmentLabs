package com.example.messengerlab1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import com.example.messengerlab1.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "SettingsFragment onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val switchTheme: Switch = binding.switchTheme

        viewLifecycleOwner.lifecycleScope.launch {
            val isDark = ThemeManager.isDarkThemeFlow(requireContext()).first()
            switchTheme.isChecked = isDark
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            viewLifecycleOwner.lifecycleScope.launch {
                ThemeManager.setDarkTheme(requireContext(), isChecked)
            }
        }
    }

    override fun onStart() { super.onStart(); Log.d("Lifecycle", "SettingsFragment onStart") }
    override fun onResume() { super.onResume(); Log.d("Lifecycle", "SettingsFragment onResume") }
    override fun onPause() { super.onPause(); Log.d("Lifecycle", "SettingsFragment onPause") }
    override fun onStop() { super.onStop(); Log.d("Lifecycle", "SettingsFragment onStop") }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "SettingsFragment onDestroy")
    }
}