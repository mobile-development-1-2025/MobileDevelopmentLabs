package com.example.messenger

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Activity создана")

        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Activity запущена")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Activity возобновлена")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Activity приостановлена")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Activity остановлена")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Activity уничтожена")
    }
}