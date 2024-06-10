package com.example.testing.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.datastore.preferences.PreferencesDatastore

val Context.testingDataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences_test")

class TestingPreferencesDatastore (
    context: Context,
): PreferencesDatastore(context.testingDataStore)