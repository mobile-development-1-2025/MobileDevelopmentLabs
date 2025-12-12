package com.example.messenger

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    // Тег для отслеживания жизненного цикла в логе
    private val TAG = "LifeCycleLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "MainActivity: onCreate()") // Логирование создания компонента

        // 1. Получаем NavController из NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // 2. Находим BottomNavigationView
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        // 3. Связываем BottomNavigationView с NavController
        bottomNav.setupWithNavController(navController)
    }

    // Дополнительное логирование для отслеживания жизненного цикла
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "MainActivity: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity: onDestroy()") // Логирование завершения работы
    }
}