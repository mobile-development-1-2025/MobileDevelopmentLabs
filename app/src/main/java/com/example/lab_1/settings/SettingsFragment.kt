package com.example.lab_1.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.lab_1.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val TAG = "SettingsFragment"

    private val vm: SettingsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); Log.i(TAG, "onCreate") }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        Log.i(TAG, "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated")

        vm.isDarkTheme.observe(viewLifecycleOwner) { isDark ->
            if (binding.switchTheme.isChecked != isDark) binding.switchTheme.isChecked = isDark
            val mode = if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
        }

        binding.switchTheme.setOnCheckedChangeListener { _, checked ->
            vm.setDarkTheme(checked)
        }
    }

    override fun onDestroyView() { Log.i(TAG, "onDestroyView"); _binding = null; super.onDestroyView() }
    override fun onDestroy() { Log.i(TAG, "onDestroy"); super.onDestroy() }
}
