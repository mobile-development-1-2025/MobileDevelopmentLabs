package com.example.lab1.ui.settings

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.lab1.R
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {

    private lateinit var themeSwitch: SwitchMaterial

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Lifecycle", "SettingsFragment onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        themeSwitch = view.findViewById(R.id.themeSwitch)

        val sharedPrefs = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isDarkMode = sharedPrefs.getBoolean("dark_mode", false)
        themeSwitch.isChecked = isDarkMode

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            sharedPrefs.edit().putBoolean("dark_mode", isChecked).apply()
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
