package com.example.messenger

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.messenger.databinding.ActivityMainBinding
import com.example.messenger.ui.ThemeManager
import com.example.messenger.viewmodel.SettingsViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var themeManager: ThemeManager
    private lateinit var viewModel: SettingsViewModel
    private var tag: String = "Main"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        themeManager = ThemeManager(this)
        viewModel = SettingsViewModel(this)
        binding = ActivityMainBinding.inflate(layoutInflater)

        themeManager.currentTheme = viewModel.getCurrentTheme()
        themeManager.applyTheme()

        setContentView(binding.root)
        setupNavigation()
        updateBottomNavigationBackground()
    }

    private fun updateBottomNavigationBackground() {
        val backgroundColor = themeManager.getBottomNavBackgroundColor()
        binding.bottomNavigation.setBackgroundColor(backgroundColor)
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
        Log.d(tag, "setupNavigation: Навигация настроена")
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun onThemeChanged() {
        updateBottomNavigationBackground()
    }
}
