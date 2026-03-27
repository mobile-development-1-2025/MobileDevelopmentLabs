package com.dak0ta.learnity.core.datastore.data.mapper

import com.dak0ta.learnity.core.datastore.proto.AppThemeProto
import com.dak0ta.learnity.core.domain.AppTheme

internal fun protoToDomainTheme(proto: AppThemeProto): AppTheme =
    when (proto) {
        AppThemeProto.LIGHT -> AppTheme.LIGHT
        AppThemeProto.DARK -> AppTheme.DARK
        else -> AppTheme.SYSTEM_DEFAULT
    }

internal fun domainToProtTheme(role: AppTheme): AppThemeProto =
    when (role) {
        AppTheme.LIGHT -> AppThemeProto.LIGHT
        AppTheme.DARK -> AppThemeProto.DARK
        AppTheme.SYSTEM_DEFAULT -> AppThemeProto.SYSTEM_DEFAULT
    }
