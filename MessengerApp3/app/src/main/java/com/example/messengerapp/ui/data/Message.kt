package com.example.messenger.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName


data class Reactions(
    val likes: Int = 0,
    val dislikes: Int = 0
)

// Type converter for Room database
class Converters {
    @TypeConverter
    fun fromReactions(reactions: Reactions?): String? {
        return reactions?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toReactions(reactionsString: String?): Reactions? {
        return reactionsString?.let { Gson().fromJson(it, Reactions::class.java) }
    }
}

@Entity(tableName = "messages")
@TypeConverters(Converters::class)
data class Message(
    @PrimaryKey
    val id: Int,

    @SerializedName("title")
    val name: String,

    @SerializedName("body")
    val email: String,

    @SerializedName("userId")
    val body: String,

    @SerializedName("reactions")
    val reactions: Reactions = Reactions(),

    @SerializedName("views")
    val views: Int = 0
)