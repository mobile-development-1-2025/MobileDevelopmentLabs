package com.dak0ta.learnity.core.navigation.compose

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.dak0ta.learnity.core.navigation.Direction
import com.dak0ta.learnity.core.navigation.directionRouteOf
import kotlin.reflect.KClass

fun NavController.navigateTo(
    directionClass: KClass<out Direction>,
    builder: (NavOptionsBuilder.() -> Unit)? = null,
) {
    val route = directionRouteOf(directionClass)
    if (builder == null) {
        this.navigate(route)
    } else {
        this.navigate(route, builder)
    }
}

fun NavOptionsBuilder.popUpToDirection(
    directionClass: KClass<out Direction>,
    saveState: Boolean = false,
    inclusive: Boolean = false,
) {
    popUpTo(directionRouteOf(directionClass)) {
        this.saveState = saveState
        this.inclusive = inclusive
    }
}
