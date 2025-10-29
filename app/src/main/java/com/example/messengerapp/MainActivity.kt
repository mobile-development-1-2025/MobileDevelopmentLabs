package com.example.messenger

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val isDark = getSharedPreferences("settings", MODE_PRIVATE)
            .getBoolean("dark_theme", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController
        val bottom = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottom.setupWithNavController(navController)

        Log.d("Lifecycle", "MainActivity onCreate")
    }

    override fun onStart() { super.onStart(); Log.d("Lifecycle", "MainActivity onStart") }
    override fun onResume() { super.onResume(); Log.d("Lifecycle", "MainActivity onResume") }
    override fun onPause() { Log.d("Lifecycle", "MainActivity onPause"); super.onPause() }
    override fun onStop() { Log.d("Lifecycle", "MainActivity onStop"); super.onStop() }
    override fun onDestroy() { Log.d("Lifecycle", "MainActivity onDestroy"); super.onDestroy() }
}
