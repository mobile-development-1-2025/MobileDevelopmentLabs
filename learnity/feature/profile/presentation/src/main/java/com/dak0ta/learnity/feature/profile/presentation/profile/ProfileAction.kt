package com.dak0ta.learnity.feature.profile.presentation.profile

import com.dak0ta.learnity.core.navigation.Direction
import kotlin.reflect.KClass

internal sealed interface ProfileAction {

    data class NavigateTo(val directionClass: KClass<out Direction>) : ProfileAction
}
