package com.example.lab1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lab1.ui.settings.SettingsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setupWithNavController(navController)

        settingsViewModel.isDarkMode.observe(this) { enabled ->
            val mode = if (enabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
        }

        Log.i("Lifecycle", "MainActivity onCreate")
    }

    override fun onStart() {
        super.onStart();
        Log.i("Lifecycle", "MainActivity onStart")
    }

    override fun onResume() {
        super.onResume();
        Log.i("Lifecycle", "MainActivity onResume")
    }

    override fun onPause() {
        super.onPause();
        Log.i("Lifecycle", "MainActivity onPause")
    }

    override fun onStop() {
        super.onStop();
        Log.i("Lifecycle", "MainActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "MainActivity onDestroy")
    }
}
