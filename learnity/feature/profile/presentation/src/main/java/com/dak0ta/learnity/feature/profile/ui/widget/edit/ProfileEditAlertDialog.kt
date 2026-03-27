package com.dak0ta.learnity.feature.profile.ui.widget.edit

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.dp

@Suppress("LongParameterList")
@Composable
internal fun ProfileEditAlertDialog(
    showDialog: MutableState<Boolean>,
    title: String = "",
    description: String = "",
    confirmText: String = "OK",
    cancelText: String = "Cancel",
    onConfirmAction: () -> Unit = { showDialog.value = false },
    onCancelAction: () -> Unit = { showDialog.value = false },
    showCancelButton: Boolean = false,
) {
    if (!showDialog.value) return
    AlertDialog(
        shape = RoundedCornerShape(8.dp),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        text = {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        onDismissRequest = {
            showDialog.value = false
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmAction()
                    showDialog.value = false
                },
            ) {
                Text(
                    text = confirmText,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        },
        dismissButton = if (showCancelButton) {
            {
                TextButton(
                    onClick = {
                        onCancelAction()
                        showDialog.value = false
                    },
                ) {
                    Text(
                        text = cancelText,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }
        } else {
            null
        },
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        textContentColor = MaterialTheme.colorScheme.onSurface,
    )
}
