package com.alva.codedelaroute.view_models

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppConfigurationViewModel(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("setting")
        val FIRST_LAUNCH_APP = booleanPreferencesKey("first_launch_app")
        val FONT_SIZE_SCALE = floatPreferencesKey("font_size_scale")
    }

    val getIsFirstLaunchApp: Flow<Boolean?> = context.dataStore.data.map { preferences ->
            preferences[FIRST_LAUNCH_APP] ?: true
        }

    suspend fun saveIsFirstLaunchApp(check: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[FIRST_LAUNCH_APP] = check
        }
    }

    val getFontSizeScale: Flow<Float?> =
        context.dataStore.data.map { preferences -> preferences[FONT_SIZE_SCALE] ?: 1.0f }

    suspend fun saveFontSizeScale(fontSizeScale: Float) {
        context.dataStore.edit { preferences ->
            preferences[FONT_SIZE_SCALE] = fontSizeScale
        }
    }
}