package com.dak0ta.learnity.core.network.data.converter

import com.dak0ta.learnity.core.domain.Gender
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

internal class UserRoleConverter {

    @FromJson
    fun fromJson(value: String): Gender = when (value.lowercase()) {
        "male" -> Gender.MALE
        "female" -> Gender.FEMALE
        else -> Gender.MALE
    }

    @ToJson
    fun toJson(role: Gender): String = when (role) {
        Gender.MALE -> "male"
        Gender.FEMALE -> "female"
    }
}
