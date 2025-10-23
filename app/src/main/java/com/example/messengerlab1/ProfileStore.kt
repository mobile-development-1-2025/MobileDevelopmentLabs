package com.example.messengerlab1

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.profileDataStore by preferencesDataStore(name = "profile")

object ProfileStore {
    private val KEY_NAME = stringPreferencesKey("name")
    private val KEY_USERNAME = stringPreferencesKey("username")
    private val KEY_EMAIL = stringPreferencesKey("email")
    private val KEY_PHONE = stringPreferencesKey("phone")
    private val KEY_BIO = stringPreferencesKey("bio")

    data class Profile(
        val name: String = "",
        val username: String = "",
        val email: String = "",
        val phone: String = "",
        val bio: String = ""
    )

    fun flow(context: Context): Flow<Profile> =
        context.profileDataStore.data.map { p ->
            Profile(
                name = p[KEY_NAME] ?: "",
                username = p[KEY_USERNAME] ?: "",
                email = p[KEY_EMAIL] ?: "",
                phone = p[KEY_PHONE] ?: "",
                bio = p[KEY_BIO] ?: ""
            )
        }

    suspend fun save(context: Context, profile: Profile) {
        context.profileDataStore.edit { p ->
            p[KEY_NAME] = profile.name
            p[KEY_USERNAME] = profile.username
            p[KEY_EMAIL] = profile.email
            p[KEY_PHONE] = profile.phone
            p[KEY_BIO] = profile.bio
        }
    }
}