package com.example.messenger.data

import android.content.Context

class MessagesPrefs(context: Context) {

    companion object {
        private const val PREFS_NAME = "messages_prefs"
        private const val KEY_LAST_MESSAGE_ID = "last_message_id"
        private const val DEFAULT_ID = 2
    }

    private val prefs =
        context.applicationContext.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

    fun getLastMessageId(): Int {
        return prefs.getInt(KEY_LAST_MESSAGE_ID, DEFAULT_ID)
    }

    fun saveLastMessageId(id: Int) {
        prefs.edit().putInt(KEY_LAST_MESSAGE_ID, id).apply()
    }
}
