package com.waycooler.messengermih.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.waycooler.messengermih.viewmodels.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel()
) {
    val tag = "SettingsScreen"
    val darkTheme = viewModel.darkTheme.observeAsState(false)

    androidx.compose.runtime.DisposableEffect(Unit) {
        Log.d(tag, "Экран настроек создан")
        onDispose {
            Log.d(tag, "Экран настроек уничтожен")
        }
    }

    Surface(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = "Настройки",
                style = MaterialTheme.typography.headlineLarge
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Тёмная тема")
                Switch(
                    checked = darkTheme.value,
                    onCheckedChange = { viewModel.setDarkTheme(it) }
                )
            }
        }
    }
}
