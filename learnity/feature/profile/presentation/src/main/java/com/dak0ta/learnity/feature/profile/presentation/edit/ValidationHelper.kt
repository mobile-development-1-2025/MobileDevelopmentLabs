package com.dak0ta.learnity.feature.profile.presentation.edit

import com.dak0ta.learnity.core.domain.User
import com.dak0ta.learnity.feature.profile.presentation.edit.ui.EditableFieldType

object ValidationHelper {

    const val MIN_NAME_LENGTH = 2
    const val MAX_NAME_LENGTH = 25
    val nameRegex = Regex("^[a-zA-Zа-яА-ЯёЁ]+([ -][a-zA-Zа-яА-ЯёЁ]+)?$")

    internal fun validateAllFields(user: User): Map<EditableFieldType, String> {
        val validatedValues = mapOf(
            EditableFieldType.FIRST_NAME to user.firstName,
            EditableFieldType.LAST_NAME to user.lastName,
        )

        return validatedValues.mapNotNull { (field, value) ->
            validateField(field, value)?.let { error -> field to error }
        }.toMap()
    }

    private fun validateField(field: EditableFieldType, value: String): String? {
        if (value.isBlank()) return "The field cannot be empty"

        return when (field) {
            EditableFieldType.FIRST_NAME, EditableFieldType.LAST_NAME -> validateName(value)
        }
    }

    private fun validateName(input: String): String? =
        when {
            input.length < MIN_NAME_LENGTH -> "The field must contain at least 2 characters"
            input.length > MAX_NAME_LENGTH -> "The field must contain no more than 25 characters"
            !nameRegex.matches(input) -> "The field contains invalid characters"
            else -> null
        }
}
