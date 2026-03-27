package com.dak0ta.learnity.feature.profile.presentation.edit.ui

sealed class ButtonState(open val title: String) {

    data class Enabled(
        override val title: String,
    ) : ButtonState(title)

    data class Disabled(
        override val title: String,
    ) : ButtonState(title)

    data class Loading(
        override val title: String,
    ) : ButtonState(title)
}
