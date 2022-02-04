package com.techjd.devconnector.Utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("datastore")

class DataStore(
    context: Context
) {
    private val applicationContext = context.applicationContext

    suspend fun saveToken(token: String) {
        val key = stringPreferencesKey("token")
        applicationContext.dataStore.edit { preferences ->
            preferences[key] = token
        }
    }

    fun getToken(): Flow<String?> {
        return applicationContext.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey("token")]
        }
    }
}
