package com.example.messenger.ui

import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.content.Context
import com.example.messenger.databinding.FragmentSettingsBinding
import android.content.SharedPreferences
import com.example.messenger.R
import com.example.messenger.MainActivity

class SettingsFragment: Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var themeManager: ThemeManager
    private lateinit var prefs: SharedPreferences
    private val tag: String = "Settings"

    companion object {
        private const val prefs_name = "app_settings"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate: Fragment создан")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(tag, "onCreateView: Создание View для Fragment")
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tag, "onViewCreated: View создан")

        val context = requireContext()
        prefs = context.getSharedPreferences(prefs_name, Context.MODE_PRIVATE)
        themeManager = ThemeManager(context)

        setup()
        loadSettings()
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart: Fragment запущен")
    }

    private fun setup() {
        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("notifications", isChecked).apply()
        }

        binding.themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val newTheme = when (checkedId) {
                R.id.theme_light -> ThemeManager.THEME_LIGHT
                R.id.theme_dark -> ThemeManager.THEME_DARK
                R.id.theme_system -> ThemeManager.THEME_SYSTEM
                else -> ThemeManager.THEME_SYSTEM
            }

            themeManager.currentTheme = newTheme
        }

        binding.resetSettingsButton.setOnClickListener {
            resetSettings()
        }

        binding.saveSettingsButton.setOnClickListener {
            themeManager.applyTheme()
            (activity as? MainActivity)?.onThemeChanged()
        }
    }

    private fun loadSettings() {
        binding.notificationsSwitch.isChecked = prefs.getBoolean("notifications", true)

        when (themeManager.currentTheme) {
            "light" -> binding.themeLight.isChecked = true
            "dark" -> binding.themeDark.isChecked = true
            else -> binding.themeSystem.isChecked = true
        }

    }

    private fun resetSettings() {
        with(prefs.edit()) {
            putBoolean("notifications", true)
            apply()
        }
        themeManager.currentTheme = ThemeManager.THEME_SYSTEM
        themeManager.applyTheme()
        loadSettings()
    }
}
