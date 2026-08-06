package com.example.messenger.ui

import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.messenger.databinding.FragmentSettingsBinding
import com.example.messenger.R
import com.example.messenger.MainActivity
import com.example.messenger.viewmodel.SettingsViewModel


class SettingsFragment: Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var viewModel: SettingsViewModel
    private val tag: String = "Settings"

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

        viewModel = SettingsViewModel(requireContext())

        setup()
        loadSettings()
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart: Fragment запущен")
    }

    private fun setup() {
        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateNotifications(isChecked)
        }

        binding.themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val newTheme = when (checkedId) {
                R.id.theme_light -> ThemeManager.THEME_LIGHT
                R.id.theme_dark -> ThemeManager.THEME_DARK
                R.id.theme_system -> ThemeManager.THEME_SYSTEM
                else -> ThemeManager.THEME_SYSTEM
            }

            viewModel.updateTheme(newTheme)
        }

        binding.resetSettingsButton.setOnClickListener {
            resetSettings()
        }

        binding.saveSettingsButton.setOnClickListener {
            viewModel.applyTheme()
//            (activity as? MainActivity)?.onThemeChanged()
        }
    }

    private fun loadSettings() {
        viewModel.settings.observe(viewLifecycleOwner) { settings ->
            when (settings.theme) {
                ThemeManager.THEME_LIGHT -> binding.themeLight.isChecked = true
                ThemeManager.THEME_DARK -> binding.themeDark.isChecked = true
                else -> binding.themeSystem.isChecked = true
            }

            binding.notificationsSwitch.isChecked = settings.notificationsEnabled
        }
    }

    private fun resetSettings() {
        viewModel.resetSettings()
    }
}
