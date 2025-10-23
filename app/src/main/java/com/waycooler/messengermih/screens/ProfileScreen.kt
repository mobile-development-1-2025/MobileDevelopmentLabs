package com.waycooler.messengermih.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.waycooler.messengermih.R
import com.waycooler.messengermih.data.User

@Composable
fun ProfileScreen() {
    val tag = "ProfileScreen"
    val user = User(
        id = 1,
        name = "Вайкулер",
        email = "waycooler@mail.ru",
        bio = "Засчитайте лабу пж :)",
        avatarUrl = ""
    )

    androidx.compose.runtime.DisposableEffect(Unit) {
        Log.d(tag, "Экран Профиля создан")
        onDispose {
            Log.d(tag, "Экран Профиля уничтожен")
        }
    }

    ProfileContent(user)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(user: User) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Профиль",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 🖼️ Локальная аватарка
            Image(
                painter = painterResource(id = R.drawable.my_avatar),
                contentDescription = "Аватар пользователя",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = user.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = user.bio,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { /* TODO: переход на экран редактирования */ }) {
                Text("Редактировать профиль")
            }
        }
    }
}
