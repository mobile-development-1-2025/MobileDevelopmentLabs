package com.example.messenger.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.messenger.R

class SettingsFragment : Fragment() {

    companion object {
        private const val TAG = "SettingsFragment"
        private const val PREFS_NAME = "messenger_prefs"
        private const val KEY_DARK_THEME = "dark_theme"
    }

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var themeSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView called")
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated called")
        
        themeSwitch = view.findViewById(R.id.theme_switch)
        setupThemeSwitch()
    }

    private fun setupThemeSwitch() {
        // Загружаем сохраненное состояние темы
        val isDarkTheme = sharedPreferences.getBoolean(KEY_DARK_THEME, false)
        themeSwitch.isChecked = isDarkTheme
        
        // Устанавливаем слушатель для переключения темы
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            Log.d(TAG, "Theme switch changed: $isChecked")
            
            // Сохраняем состояние темы
            sharedPreferences.edit().putBoolean(KEY_DARK_THEME, isChecked).apply()
            
            // Применяем тему
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }
}
