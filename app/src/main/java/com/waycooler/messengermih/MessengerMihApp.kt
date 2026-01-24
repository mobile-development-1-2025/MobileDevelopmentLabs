package com.waycooler.messengermih

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.waycooler.messengermih.navigation.Screen
import com.waycooler.messengermih.screens.FeedScreen
import com.waycooler.messengermih.screens.ProfileScreen
import com.waycooler.messengermih.screens.SettingsScreen
import com.waycooler.messengermih.ui.theme.MessengerMihTheme
import com.waycooler.messengermih.viewmodels.SettingsViewModel

@Composable
fun MessengerMihApp() {
    val settingsViewModel: SettingsViewModel =
        viewModel(factory = ViewModelProvider.AndroidViewModelFactory(LocalContext.current.applicationContext as Application))

    val darkTheme = settingsViewModel.darkTheme.observeAsState(false).value

    MessengerMihTheme(darkTheme = darkTheme) {
        val navController = rememberNavController()
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry?.destination?.route

        Scaffold(
            bottomBar = {
                NavigationBar {
                    Screen.items.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.label) },
                            label = { Text(screen.label) },
                            selected = currentDestination == screen.route,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Feed.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Feed.route) { FeedScreen() }
                composable(Screen.Profile.route) { ProfileScreen() }
                composable(Screen.Settings.route) {
                    SettingsScreen(
                        viewModel = settingsViewModel
                    )
                }
            }
        }
    }
}
