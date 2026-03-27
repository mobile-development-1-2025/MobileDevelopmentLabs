package com.dak0ta.learnity.feature.authorization.presentation

import com.dak0ta.learnity.core.navigation.Direction
import kotlin.reflect.KClass

internal sealed interface AuthorizationAction {

    data class NavigateTo(val directionClass: KClass<out Direction>) : AuthorizationAction
}
