package com.dak0ta.learnity.core.database.data.converter

import androidx.room.TypeConverter
import com.dak0ta.learnity.core.domain.Gender

internal class GenderConverter {

    @TypeConverter
    fun fromGender(role: Gender): String = role.name.lowercase()

    @TypeConverter
    fun toGender(role: String): Gender = Gender.valueOf(role.uppercase())
}
