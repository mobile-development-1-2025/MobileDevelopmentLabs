package com.waycooler.messengermih.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsDataStore(private val context: Context) {

    private val THEME_KEY = booleanPreferencesKey("dark_theme")

    val darkThemeFlow = context.dataStore.data.map { prefs ->
        prefs[THEME_KEY] ?: false
    }

    suspend fun saveDarkTheme(value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[THEME_KEY] = value
        }
    }
}
