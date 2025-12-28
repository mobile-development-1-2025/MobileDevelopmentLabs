package com.waycooler.messengermih.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.waycooler.messengermih.viewmodels.FeedViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    viewModel: FeedViewModel = viewModel()
) {
    val messages by viewModel.messages.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)

    var showUpdateSuccess by remember { mutableStateOf(false) }

    LaunchedEffect(showUpdateSuccess) {
        if (showUpdateSuccess) {
            delay(2000)
            showUpdateSuccess = false
        }
    }

    Scaffold(
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ExtendedFloatingActionButton(
                    onClick = {
                        if (!isLoading) {
                            viewModel.clearDatabase()
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Очистить"
                        )
                    },
                    text = { Text("Очистить") },
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                    expanded = !isLoading
                )

                ExtendedFloatingActionButton(
                    onClick = {
                        if (!isLoading) {
                            viewModel.refreshMessages()
                            showUpdateSuccess = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Обновить"
                        )
                    },
                    text = { Text("Обновить") },
                    expanded = !isLoading
                )
            }
        }
    ) { _ ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Лента сообщений",
                            style = MaterialTheme.typography.headlineLarge
                        )

                        if (showUpdateSuccess) {
                            Surface(
                                shape = MaterialTheme.shapes.small,
                                tonalElevation = 2.dp,
                                color = MaterialTheme.colorScheme.primaryContainer
                            ) {
                                Text(
                                    text = "Обновлено!",
                                    style = MaterialTheme.typography.labelSmall,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }

                    if (messages.isEmpty() && !isLoading) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("Нет сообщений")
                                Button(
                                    onClick = {
                                        viewModel.refreshMessages()
                                        showUpdateSuccess = true
                                    },
                                    enabled = true
                                ) {
                                    Text("Загрузить сообщения")
                                }
                            }
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(messages) { message ->
                                MessageItem(
                                    message = message,
                                    onLikeClick = {
                                        viewModel.onLikeClicked(message.id)
                                    }
                                )
                            }
                        }
                    }
                }

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}