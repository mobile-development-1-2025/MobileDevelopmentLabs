package com.dak0ta.learnity.feature.profile.ui.edit

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dak0ta.learnity.core.navigation.compose.LocalNavController
import com.dak0ta.learnity.core.navigation.compose.LocalViewModelFactory
import com.dak0ta.learnity.feature.profile.presentation.edit.ProfileEditAction
import com.dak0ta.learnity.feature.profile.presentation.edit.ProfileEditViewModel
import com.dak0ta.learnity.feature.profile.ui.widget.edit.ProfileEditAlertDialog
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
internal fun ProfileEditScreen() {
    val viewModelFactory = LocalViewModelFactory.current
    val navController = LocalNavController.current
    val viewModel: ProfileEditViewModel = viewModel(factory = viewModelFactory)
    val state by viewModel.uiState.collectAsState()
    val showDialog = remember { mutableStateOf(false) }
    val dialogTitle = remember { mutableStateOf<String?>(null) }
    val dialogDescription = remember { mutableStateOf<String?>(null) }

    DisposableEffect(Unit) {
        Log.d("Learnity:ProfileEditScreen", "Composable created")
        onDispose {
            Log.d("Learnity:ProfileEditScreen", "Composable disposed")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initialize()

        viewModel.action
            .onEach { action ->
                when (action) {
                    is ProfileEditAction.NavigateBack -> {
                        navController.popBackStack()
                    }

                    is ProfileEditAction.ShowMessage -> {
                        showDialog.value = true
                        dialogTitle.value = action.title
                        dialogDescription.value = action.description
                    }
                }
            }
            .launchIn(this)
    }

    ProfileEditAlertDialog(
        showDialog = showDialog,
        title = dialogTitle.value ?: "",
        description = dialogDescription.value ?: "",
    )

    ProfileScreenContent(
        state = state,
        onValueChange = viewModel::onValueChange,
        onClearClick = viewModel::onClearClick,
        onSaveClick = viewModel::onSaveClick,
        onRetryClick = viewModel::onRetryClick,
        onBackClick = navController::popBackStack
    )
}
