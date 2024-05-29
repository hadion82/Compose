package com.example.datastore.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")

@Singleton
class PagingPreferencesDatastore @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val dataStore = context.dataStore
    val pagingKey = dataStore.intPreference("paging_key")
}

