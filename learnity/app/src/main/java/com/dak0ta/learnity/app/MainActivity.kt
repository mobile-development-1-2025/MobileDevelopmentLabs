package com.dak0ta.learnity.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dak0ta.learnity.app.di.App
import com.dak0ta.learnity.app.di.appComponent
import com.dak0ta.learnity.app.navigation.BottomNavBar
import com.dak0ta.learnity.app.navigation.NavHost
import com.dak0ta.learnity.app.navigation.bottomNavItems
import com.dak0ta.learnity.app.ui.theme.LearnityTheme
import com.dak0ta.learnity.app.worker.QuotesWorkerScheduler
import com.dak0ta.learnity.core.datastore.domain.usecase.theme.GetAppThemeUseCase
import com.dak0ta.learnity.core.datastore.domain.usecase.theme.ObserveAppThemeUseCase
import com.dak0ta.learnity.core.domain.AppTheme
import com.dak0ta.learnity.core.navigation.directionRouteOf

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Activity created")

        requestNotificationPermissionIfNeeded()
        QuotesWorkerScheduler.schedule(this)

        lifecycle.addObserver(
            object : DefaultLifecycleObserver {
                override fun onStart(owner: LifecycleOwner) {
                    Log.d(TAG, "Activity started")
                }

                override fun onStop(owner: LifecycleOwner) {
                    Log.d(TAG, "Activity stopped")
                }

                override fun onDestroy(owner: LifecycleOwner) {
                    Log.d(TAG, "Activity destroyed")
                }
            },
        )
        enableEdgeToEdge()
        setContent {
            val app = application as App
            val getAppThemeUseCase: GetAppThemeUseCase = app.appComponent.getAppThemeUseCase()
            val observeAppThemeUseCase: ObserveAppThemeUseCase = app.appComponent.observeAppThemeUseCase()

            val appTheme by produceState(initialValue = AppTheme.SYSTEM_DEFAULT) {
                val initial = runCatching { getAppThemeUseCase() }.getOrDefault(AppTheme.SYSTEM_DEFAULT)
                value = initial

                observeAppThemeUseCase().collect { theme ->
                    value = theme
                }
            }

            LearnityTheme(
                darkTheme = when (appTheme) {
                    AppTheme.DARK -> true
                    AppTheme.LIGHT -> false
                    AppTheme.SYSTEM_DEFAULT -> isSystemInDarkTheme()
                },
            ) {
                val navController = rememberNavController()
                val viewModelFactory = appComponent.viewModelFactory()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        val routesToShowBottomBar = bottomNavItems.map { directionRouteOf(it.directionClass) }

                        val shouldShowBottomBar = currentDestination?.hierarchy
                            ?.any { dest -> routesToShowBottomBar.contains(dest.route) } == true

                        if (shouldShowBottomBar) {
                            BottomNavBar(navController = navController, items = bottomNavItems)
                        }
                    },
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        viewModelFactory = viewModelFactory,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermission.launch(
                    Manifest.permission.POST_NOTIFICATIONS,
                )
            }
        }
    }

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                Log.d(TAG, "Notification permission granted")
            } else {
                Log.d(TAG, "Notification permission denied")
            }
        }

    private companion object {

        const val TAG = "Learnity:MainActivity"
    }
}
