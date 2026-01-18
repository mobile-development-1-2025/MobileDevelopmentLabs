package com.example.messenger

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.messenger.viewmodel.SettingsViewModel

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val PREFS_NAME = "messenger_prefs"
        private const val KEY_DARK_THEME = "dark_theme"
    }

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        
        settingsViewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        
        loadSavedTheme()
        observeThemeChanges()
        applyTheme(settingsViewModel.isDarkTheme.value ?: false)
        
        setContentView(R.layout.activity_main)
        
        setupBottomNavigation()
    }

    private fun loadSavedTheme() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, 0)
        val isDarkTheme = sharedPreferences.getBoolean(KEY_DARK_THEME, false)
        settingsViewModel.setDarkTheme(isDarkTheme)
    }

    private fun observeThemeChanges() {
        settingsViewModel.isDarkTheme.observe(this) { isDarkTheme ->
            applyTheme(isDarkTheme)
            saveTheme(isDarkTheme)
        }
    }

    private fun applyTheme(isDarkTheme: Boolean) {
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun saveTheme(isDarkTheme: Boolean) {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, 0)
        sharedPreferences.edit().putBoolean(KEY_DARK_THEME, isDarkTheme).apply()
    }

    private fun setupBottomNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)
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

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }
}