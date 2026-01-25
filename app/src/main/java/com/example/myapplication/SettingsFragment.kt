package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.example.myapplication.viewmodel.SettingsViewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        viewModel = ViewModelProvider(requireActivity())[SettingsViewModel::class.java]

        val themeSwitch = view.findViewById<SwitchMaterial>(R.id.themeSwitch)

        viewModel.darkMode.observe(viewLifecycleOwner) { isDark ->
            if (themeSwitch.isChecked != isDark) {
                themeSwitch.isChecked = isDark
            }
            AppCompatDelegate.setDefaultNightMode(
                if (isDark) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setDarkMode(isChecked)
        }

        Log.i("Lifecycle", "SettingsFragment onViewCreated")
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("Lifecycle", "SettingsFragment onDestroyView")
    }
}