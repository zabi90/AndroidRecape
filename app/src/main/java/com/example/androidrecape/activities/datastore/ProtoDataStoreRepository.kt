package com.example.androidrecape.activities.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.androidrecape.AppSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProtoDataStoreRepository constructor(val context: Context) {

    private val Context.appSettings: DataStore<AppSettings> by dataStore(
        fileName = "appSettings.pb",
        serializer = SettingsSerializer
    )

    val exampleCounterFlow: Flow<Int> = context.appSettings.data
        .map { appSettings ->
            // The exampleCounter property is generated from the proto schema.
            appSettings.exampleCounter
        }

    suspend fun incrementCounter() {
        context.appSettings.updateData { currentAppSettings ->
            currentAppSettings.toBuilder()
                .setExampleCounter(currentAppSettings.exampleCounter + 1)
                .build()
        }
    }
}