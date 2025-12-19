package com.example.messengerapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.messengerapp.R
import com.google.android.material.materialswitch.MaterialSwitch

class SettingsFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "onCreate SettingsFragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.d("Lifecycle", "onCreateView SettingsFragment")
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Lifecycle", "onViewCreated SettingsFragment")

        val themeSwitch = view.findViewById<MaterialSwitch>(R.id.theme_switch)

        viewModel.isDarkThemeEnabled.observe(viewLifecycleOwner) { isDark ->
            Log.d("SettingsFragment", "observer isDarkThemeEnabled = $isDark")

            if (themeSwitch.isChecked != isDark) {
                themeSwitch.isChecked = isDark
            }

            val mode = if (isDark) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(mode)
        }

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d("SettingsFragment", "user toggled switch: $isChecked")
            viewModel.setDarkThemeEnabled(isChecked)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "onDestroy SettingsFragment")
    }
}
