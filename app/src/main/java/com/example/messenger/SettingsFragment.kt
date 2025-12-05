package com.example.messenger

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class SettingsFragment : Fragment() {
    companion object {
        private const val TAG = "SettingsFragment"
    }

    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var themeSwitch: Switch
    private lateinit var tvThemeStatus: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")

        // Инициализируем ViewModel
        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Находим View элементы
        themeSwitch = view.findViewById(R.id.switch_theme)
        tvThemeStatus = view.findViewById(R.id.tv_theme_status)

        // Наблюдаем за изменениями темы
        settingsViewModel.isDarkTheme.observe(viewLifecycleOwner) { isDark ->
            themeSwitch.isChecked = isDark
            tvThemeStatus.text = if (isDark) "Темная тема" else "Светлая тема"
            Log.d(TAG, "Тема обновлена: ${if (isDark) "темная" else "светлая"}")
        }

        // Обработчик переключателя
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.setTheme(isChecked)
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}