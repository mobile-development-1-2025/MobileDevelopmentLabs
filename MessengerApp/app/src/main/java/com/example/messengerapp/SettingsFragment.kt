package com.example.messengerapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.messengerapp.viewmodel.SettingsViewModel
import com.google.android.material.switchmaterial.SwitchMaterial
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit


class SettingsFragment : Fragment() {

    private val settingsViewModel: SettingsViewModel by viewModels()
    private var ignoreListener = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val darkTheme = prefs.getBoolean("dark_theme", false)
        settingsViewModel.isDarkTheme.value = darkTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchTheme = view.findViewById<SwitchMaterial>(R.id.switchTheme)
        val prefs = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Настройки"
        toolbar.setTitleTextColor(resources.getColor(android.R.color.white, requireContext().theme))

        settingsViewModel.isDarkTheme.observe(viewLifecycleOwner) { isDark ->
            ignoreListener = true
            if (switchTheme.isChecked != isDark) {
                switchTheme.isChecked = isDark
            }
            ignoreListener = false
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (ignoreListener) return@setOnCheckedChangeListener

            settingsViewModel.isDarkTheme.value = isChecked
            prefs.edit { putBoolean("dark_theme", isChecked) }

            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }
}
