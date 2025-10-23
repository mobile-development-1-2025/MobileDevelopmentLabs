package com.waycooler.messengermih.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FeedScreen() {
    val tag = "FeedScreen"

    androidx.compose.runtime.DisposableEffect(Unit) {
        Log.d(tag, "Экран новостей создан")
        onDispose {
            Log.d(tag, "Экран новостей уничтожен")
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Новостная лента",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}
