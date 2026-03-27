package com.dak0ta.learnity.feature.profile.ui.widget.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dak0ta.learnity.feature.profile.presentation.edit.ui.ButtonState
import com.dak0ta.learnity.feature.profile.presentation.edit.ui.EditableFieldType
import com.dak0ta.learnity.feature.profile.presentation.edit.ui.FieldState
import com.dak0ta.learnity.feature.profile.presentation.edit.ui.ProfileEditUiState

@Composable
internal fun EditColumn(
    state: ProfileEditUiState.Content,
    onValueChange: (EditableFieldType, String) -> Unit,
    onClearClick: (EditableFieldType) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.firstNameField.value,
            onValueChange = { newValue -> onValueChange(EditableFieldType.FIRST_NAME, newValue) },
            enabled = state.firstNameField !is FieldState.Disabled,
            label = { Text(state.firstNameField.title) },
            isError = state.firstNameField is FieldState.Invalid,
            supportingText = {
                (state.firstNameField as? FieldState.Invalid)?.errorMessage?.let { Text(it) }
            },
            trailingIcon = {
                if (state.firstNameField.value.isNotEmpty()) {
                    IconButton(onClick = { onClearClick(EditableFieldType.FIRST_NAME) }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.lastNameField.value,
            onValueChange = { newValue -> onValueChange(EditableFieldType.LAST_NAME, newValue) },
            enabled = state.lastNameField !is FieldState.Disabled,
            label = { Text(state.lastNameField.title) },
            isError = state.lastNameField is FieldState.Invalid,
            supportingText = {
                (state.lastNameField as? FieldState.Invalid)?.errorMessage?.let { Text(it) }
            },
            trailingIcon = {
                if (state.lastNameField.value.isNotEmpty()) {
                    IconButton(onClick = { onClearClick(EditableFieldType.LAST_NAME) }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            onClick = onSaveClick,
            enabled = state.button is ButtonState.Enabled,
        ) {
            Text(text = state.button.title)
        }
    }
}
