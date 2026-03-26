package com.example.lab1.ui.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.lab1.R
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {

    private val viewModel: SettingsViewModel by activityViewModels()
    private lateinit var themeSwitch: SwitchMaterial

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        themeSwitch = view.findViewById(R.id.themeSwitch)

        viewModel.isDarkMode.observe(viewLifecycleOwner) { enabled ->
            themeSwitch.isChecked = enabled
        }

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setDarkMode(isChecked)
            val mode = if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
        }

        return view
    }

    override fun onStart() {
        super.onStart();
        Log.i("Lifecycle", "SettingsFragment onStart")
    }

    override fun onResume() {
        super.onResume();
        Log.i("Lifecycle", "SettingsFragment onResume")
    }

    override fun onPause() {
        super.onPause();
        Log.i("Lifecycle", "SettingsFragment onPause")
    }

    override fun onStop() {
        super.onStop();
        Log.i("Lifecycle", "SettingsFragment onStop")
    }

    override fun onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "SettingsFragment onDestroy")
    }
}