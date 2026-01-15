package com.example.lab1.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class LikesStorage(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREF_NAME,
        Context.MODE_PRIVATE
    )

    fun isLiked(messageId: Long): Boolean {
        return prefs.getBoolean(getKey(messageId), false)
    }

    fun toggleLike(messageId: Long): Boolean {
        val currentState = isLiked(messageId)
        prefs.edit {
            putBoolean(getKey(messageId), !currentState)
        }
        return !currentState
    }

    fun getLikedMessageIds(): Set<Long> {
        val likedIds = mutableSetOf<Long>()
        prefs.all.keys.forEach { key ->
            if (key.startsWith(KEY_PREFIX) && prefs.getBoolean(key, false)) {
                val messageId = key.removePrefix(KEY_PREFIX).toLongOrNull()
                messageId?.let { likedIds.add(it) }
            }
        }
        return likedIds
    }

    private fun getKey(messageId: Long): String = "$KEY_PREFIX$messageId"

    companion object {
        private const val PREF_NAME = "likes_prefs"
        private const val KEY_PREFIX = "message_liked_"
    }
}
