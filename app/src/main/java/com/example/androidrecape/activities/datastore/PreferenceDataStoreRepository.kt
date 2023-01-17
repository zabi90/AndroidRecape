package com.example.androidrecape.activities.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceDataStoreRepository constructor(private val context: Context) {
    // At the top level of your kotlin file:
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)


    suspend fun saveUserName(userName: String) {
        context.dataStore.edit {
            it[user_name] = userName
        }
    }

    suspend fun getUserName(): Flow<String?> {
        return context.dataStore.data.map {
            it[user_name]
        }
    }

    companion object {
        const val PREFERENCE_NAME = "user_settings"
        val user_name = stringPreferencesKey("user_name")
    }
}