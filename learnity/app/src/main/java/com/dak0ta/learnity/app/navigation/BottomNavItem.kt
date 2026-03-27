package com.dak0ta.learnity.app.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.dak0ta.learnity.core.navigation.Direction
import kotlin.reflect.KClass

data class BottomNavItem(
    val directionClass: KClass<out Direction>,
    val label: String,
    val icon: ImageVector,
)
