package com.example.messenger

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.messenger.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

/**
 * Главная Activity приложения
 * Управляет навигацией и запросом разрешений
 */
class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"

    // Регистрация launcher для запроса разрешений
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach { entry ->
            val permissionName = entry.key
            val isGranted = entry.value
            
            Log.d(TAG, "Разрешение $permissionName: ${if (isGranted) "предоставлено" else "отклонено"}")
            
            if (!isGranted) {
                showPermissionRationale(permissionName)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: MainActivity создана")
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        requestPermissions()
    }

    /**
     * Настройка навигации
     */
    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.bottomNavView
        
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        
        navView.setupWithNavController(navController)
    }

    /**
     * Запрос необходимых разрешений при запуске приложения
     */
    private fun requestPermissions() {
        Log.d(TAG, "Запрос разрешений")
        
        val permissionsToRequest = mutableListOf<String>()
        
        // Разрешение на уведомления (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        
        // Разрешение на чтение контактов
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(Manifest.permission.READ_CONTACTS)
        }
        
        // Запрашиваем разрешения если есть что запрашивать
        if (permissionsToRequest.isNotEmpty()) {
            Log.d(TAG, "Запрашиваем разрешения: $permissionsToRequest")
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            Log.d(TAG, "Все разрешения уже предоставлены")
        }
    }

    /**
     * Показать объяснение для отклоненного разрешения
     */
    private fun showPermissionRationale(permission: String) {
        val message = when (permission) {
            Manifest.permission.POST_NOTIFICATIONS -> 
                "Разрешение на уведомления нужно для получения информации о синхронизации данных"
            Manifest.permission.READ_CONTACTS -> 
                "Разрешение на контакты позволит отображать имена отправителей"
            else -> "Это разрешение необходимо для полноценной работы приложения"
        }
        
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
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
