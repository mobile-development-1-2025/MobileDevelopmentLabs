package com.example.messenger

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.messenger.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: MainActivity создана")
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavView
        
        // Получаем NavHostFragment и NavController правильным способом для FragmentContainerView
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        
        // Подключаем BottomNavigationView к NavController
        navView.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: MainActivity стартовала")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: MainActivity возобновлена")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: MainActivity приостановлена")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: MainActivity остановлена")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: MainActivity уничтожена")
    }
}
