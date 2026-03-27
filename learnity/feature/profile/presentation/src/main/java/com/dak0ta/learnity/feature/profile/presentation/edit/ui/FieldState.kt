package com.dak0ta.learnity.feature.profile.presentation.edit.ui

sealed class FieldState(open val title: String, open val value: String) {

    data class Valid(
        override val title: String,
        override val value: String,
    ) : FieldState(title, value)

    data class Invalid(
        override val title: String,
        override val value: String,
        val errorMessage: String,
    ) : FieldState(title, value)

    data class Disabled(
        override val title: String,
        override val value: String,
    ) : FieldState(title, value)
}
