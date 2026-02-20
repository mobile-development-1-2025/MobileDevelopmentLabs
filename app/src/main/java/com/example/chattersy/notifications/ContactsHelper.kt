package com.example.chattersy.notifications

import android.content.Context
import android.provider.ContactsContract

object ContactsHelper {

    fun getContactCount(context: Context): Int? {
        if (!AppPermissions.hasContactsPermission(context)) return null
        return try {
            context.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                arrayOf(ContactsContract.Contacts._ID),
                null,
                null,
                null
            )?.use { it.count } ?: 0
        } catch (_: SecurityException) {
            null
        }
    }
}
