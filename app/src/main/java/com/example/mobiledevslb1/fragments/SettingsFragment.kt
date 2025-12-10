package com.example.mobiledevslb1.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import com.example.mobiledevslb1.R
import androidx.lifecycle.ViewModelProvider
import com.example.mobiledevslb1.viewmodels.SettingViewModel

class SettingsFragment: FragmentLogged() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_layout, container, false)
    }

    private val _viewModel: SettingViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val changeThemeButton = view.findViewById<Button>(R.id.changeThemes)
        changeThemeButton.setOnClickListener {
            _viewModel.changeIsDarkTheme()
        }

        _viewModel.isDarkTheme.observe(viewLifecycleOwner) { isDark ->
            val currentTheme = AppCompatDelegate.getDefaultNightMode()
            if (
                (currentTheme == AppCompatDelegate.MODE_NIGHT_YES && !isDark) ||
                (currentTheme == AppCompatDelegate.MODE_NIGHT_NO && isDark) ||
                (currentTheme == -100)
                )
            {
                AppCompatDelegate.setDefaultNightMode(
                    if (isDark) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )
                Log.d("ViewModel", "Theme changed")
            }
        }
    }
}