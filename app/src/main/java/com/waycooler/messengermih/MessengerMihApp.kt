package com.waycooler.messengermih

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.waycooler.messengermih.data.ThemePreferences
import com.waycooler.messengermih.navigation.Screen
import com.waycooler.messengermih.screens.FeedScreen
import com.waycooler.messengermih.screens.ProfileScreen
import com.waycooler.messengermih.screens.SettingsScreen
import com.waycooler.messengermih.ui.theme.MessengerMihTheme
import kotlinx.coroutines.launch

@Composable
fun MessengerMihApp() {
    val context = LocalContext.current
    val themePrefs = remember { ThemePreferences(context.applicationContext) }

    val isDarkTheme by themePrefs.isDarkTheme.collectAsState(initial = false)
    val scope = rememberCoroutineScope()

    MessengerMihTheme(darkTheme = isDarkTheme) {
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
                        darkTheme = isDarkTheme,
                        onThemeChange = { newTheme ->
                            scope.launch {
                                themePrefs.saveTheme(newTheme)
                            }
                        }
                    )
                }
            }
        }
    }
}
