package com.dak0ta.learnity.core.design

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.hideKeyboardOnTap(focusManager: FocusManager) = pointerInput(Unit) {
    detectTapGestures {
        focusManager.clearFocus()
    }
}
