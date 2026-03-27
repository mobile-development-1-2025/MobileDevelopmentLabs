package com.dak0ta.learnity.core.database.data.converter

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

internal class JsonListStringConverter {

    private val moshi: Moshi = Moshi.Builder().build()
    private val listType = Types.newParameterizedType(List::class.java, String::class.java)
    private val listAdapter: JsonAdapter<List<String>> = moshi.adapter(listType)

    @TypeConverter
    fun listToJson(list: List<String>?): String? =
        try {
            list?.let { listAdapter.toJson(it) }
        } catch (_: Exception) {
            null
        }

    @TypeConverter
    fun jsonToList(json: String?): List<String>? =
        try {
            json?.let { listAdapter.fromJson(it) }
        } catch (_: Exception) {
            null
        }
}
