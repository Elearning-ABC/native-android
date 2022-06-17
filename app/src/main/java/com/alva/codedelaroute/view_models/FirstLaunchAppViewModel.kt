package com.alva.codedelaroute.view_models

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FirstLaunchAppViewModel(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("setting")
        val FIRST_LAUNCH_APP = booleanPreferencesKey("first_launch_app")
    }

    val getBool: Flow<Boolean?> = context.dataStore.data
        .map { preferences ->
            preferences[FIRST_LAUNCH_APP] ?: true
        }
    suspend fun saveBool(check: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[FIRST_LAUNCH_APP] = check
        }
    }
}