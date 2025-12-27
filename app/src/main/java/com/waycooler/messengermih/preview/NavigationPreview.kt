package com.waycooler.messengermih.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.waycooler.messengermih.navigation.Screen
import com.waycooler.messengermih.screens.FeedScreen
import com.waycooler.messengermih.screens.ProfileScreen
import com.waycooler.messengermih.screens.SettingsScreen
import com.waycooler.messengermih.ui.theme.MessengerMihTheme

@Preview(showBackground = true)
@Composable
fun NavigationGraphPreview() {

    MessengerMihTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Screen.Feed.route
        ) {
            composable(Screen.Feed.route) { FeedScreen() }
            composable(Screen.Profile.route) { ProfileScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
        }
    }
}
