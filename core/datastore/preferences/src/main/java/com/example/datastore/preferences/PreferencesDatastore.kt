package com.example.datastore.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.datastore.preferences.di.LocalPreferences
import javax.inject.Inject
import javax.inject.Singleton

internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

@Singleton
open class PreferencesDatastore @Inject constructor(
    @LocalPreferences dataStore: DataStore<Preferences>
) {
    val pagingKey = dataStore.intPreference("paging_key")
    val totalCount = dataStore.intPreference("total_count")
}

