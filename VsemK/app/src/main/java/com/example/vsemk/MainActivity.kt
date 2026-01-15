package com.example.vsemk

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.vsemk.ui.settings.SettingsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        

        settingsViewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        applyTheme(settingsViewModel.isDarkTheme.value ?: false)
        

        settingsViewModel.isDarkTheme.observe(this) { isDark ->
            applyTheme(isDark)
            Log.d(TAG, "Тема изменена: ${if (isDark) "тёмная" else "светлая"}")
        }
        
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.nav_host_fragment).post {
            val navController = findNavController(R.id.nav_host_fragment)
            findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)
        }

        Log.d(TAG, "onCreate")
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

    private fun applyTheme(isDark: Boolean) {
        val mode = if (isDark) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}