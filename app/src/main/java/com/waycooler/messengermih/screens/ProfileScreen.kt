package com.waycooler.messengermih.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.waycooler.messengermih.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = viewModel()) {
    val tag = "ProfileScreen"

    val name = profileViewModel.name.observeAsState("Имя по умолчанию")
    val status = profileViewModel.status.observeAsState("Статус по умолчанию")

    DisposableEffect(Unit) {
        Log.d(tag, "Экран Профиля создан")
        onDispose { Log.d(tag, "Экран Профиля уничтожен") }
    }

    ProfileContent(
        name = name.value,
        status = status.value,
        onNameChange = { profileViewModel.updateName(it) },
        onStatusChange = { profileViewModel.updateStatus(it) },
        onSaveClick = {
            Log.d(tag, "Сохраняем: имя=${name.value}, статус=${status.value}")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    name: String,
    status: String,
    onNameChange: (String) -> Unit,
    onStatusChange: (String) -> Unit,
    onSaveClick: () -> Unit
) {
    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Профиль", style = MaterialTheme.typography.headlineLarge)

            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Имя") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = status,
                onValueChange = onStatusChange,
                label = { Text("Статус") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = onSaveClick,
                modifier = Modifier.align(alignment = androidx.compose.ui.Alignment.Start)
            ) {
                Text("Сохранить")
            }
        }
    }
}
