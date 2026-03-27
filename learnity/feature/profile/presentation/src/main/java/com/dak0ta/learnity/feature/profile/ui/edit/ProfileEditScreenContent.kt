package com.dak0ta.learnity.feature.profile.ui.edit

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.dak0ta.learnity.core.design.ErrorScreen
import com.dak0ta.learnity.core.design.LoadingScreen
import com.dak0ta.learnity.core.design.hideKeyboardOnTap
import com.dak0ta.learnity.feature.profile.presentation.edit.ui.EditableFieldType
import com.dak0ta.learnity.feature.profile.presentation.edit.ui.ProfileEditUiState
import com.dak0ta.learnity.feature.profile.ui.widget.edit.EditColumn

@Suppress("LongParameterList")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProfileScreenContent(
    state: ProfileEditUiState,
    onValueChange: (EditableFieldType, String) -> Unit,
    onClearClick: (EditableFieldType) -> Unit,
    onSaveClick: () -> Unit,
    onRetryClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    when (state) {
        is ProfileEditUiState.Loading -> {
            LoadingScreen()
        }

        is ProfileEditUiState.Content -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        windowInsets = WindowInsets(0.dp),
                        title = { Text(text = state.title) },
                        navigationIcon = {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null,
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background,
                        ),
                    )
                },
                modifier = Modifier.hideKeyboardOnTap(LocalFocusManager.current),
            ) { innerPadding ->
                EditColumn(
                    state = state,
                    onValueChange = onValueChange,
                    onClearClick = onClearClick,
                    onSaveClick = onSaveClick,
                    modifier = Modifier.padding(innerPadding),
                )
            }
        }

        is ProfileEditUiState.Error -> {
            ErrorScreen(
                title = state.title,
                description = state.description,
                retryButtonText = state.retryButtonText,
                onRetryClick = onRetryClick,
            )
        }
    }
}
