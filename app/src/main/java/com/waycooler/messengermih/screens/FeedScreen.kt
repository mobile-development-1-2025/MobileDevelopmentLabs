package com.waycooler.messengermih.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.waycooler.messengermih.viewmodels.FeedViewModel

@Composable
fun FeedScreen(
    viewModel: FeedViewModel = viewModel()
) {
    val tag = "FeedScreen"

    androidx.compose.runtime.DisposableEffect(Unit) {
        Log.d(tag, "Экран новостей создан")
        onDispose {
            Log.d(tag, "Экран новостей уничтожен")
        }
    }

    val messages = viewModel.messages.observeAsState(emptyList()).value

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Button(
            onClick = { viewModel.loadMessages() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Обновить")
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(messages) { message ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = message.author,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = message.text)
                    }
                }
            }
        }
    }
}
