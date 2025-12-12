package com.example.mymessenger

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mymessenger.databinding.ActivityMainBinding
import com.example.mymessenger.viewModel.SettingsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        android.util.Log.d("Lifecycle", "com.example.mymessenger.MainActivity created")

        settingsViewModel.init(applicationContext)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.newsFragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        android.util.Log.d("Lifecycle", "com.example.mymessenger.MainActivity destroyed")
    }
}