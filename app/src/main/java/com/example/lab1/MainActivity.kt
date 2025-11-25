package com.example.lab1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.lab1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализируем биндинг для нашего макета activity_main.xml
        binding = ActivityMainBinding.inflate(layoutInflater)
        // Устанавливаем его как основной вид для этого Activity
        setContentView(binding.root)

        // Устанавливаем Toolbar из нашего макета как главный ActionBar
        setSupportActionBar(binding.toolbar)

        // Находим хост навигации (контейнер для фрагментов)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController

        // Определяем конфигурацию верхних экранов.
        // Это нужно, чтобы на них не было стрелки "назад".
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_feed, R.id.navigation_profile, R.id.navigation_settings
            )
        )

        // Связываем ActionBar с NavController, чтобы заголовок менялся автоматически
        setupActionBarWithNavController(navController, appBarConfiguration)

        // САМАЯ ГЛАВНАЯ ЧАСТЬ:
        // Связываем наш BottomNavigationView (нижнюю панель) с NavController.
        // Теперь при нажатии на вкладки будут открываться нужные фрагменты.
        binding.bottomNavView.setupWithNavController(navController)
    }
}
