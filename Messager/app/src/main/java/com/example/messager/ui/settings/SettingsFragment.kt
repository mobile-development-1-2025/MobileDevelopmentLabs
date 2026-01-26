package com.example.messager.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.messager.databinding.FragmentSettingsBinding
import com.example.messager.utils.ThemeManager

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        Log.d("Settings", "onCreateView")
        val isDark = ThemeManager.isDarkModeEnabled(requireContext())
        binding.switchDarkMode.isChecked = isDark

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            ThemeManager.setDarkMode(requireContext(), isChecked)
        }
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Settings", "onCreate")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Settings", "onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Settings", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Settings", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Settings", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Settings", "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("Settings", "onDestroyView")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Settings", "onDestroy")
    }
}