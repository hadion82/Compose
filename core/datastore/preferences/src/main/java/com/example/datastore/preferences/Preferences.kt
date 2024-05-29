package com.example.datastore.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class Preference<T : Any, out P : Preferences.Key<T>>(
    private val dataStore: DataStore<Preferences>,
    private val key: P,
    private val defaultValue: T
) {

    val data = dataStore.data
        .map { preferences -> preferences[key] ?: defaultValue }

    suspend fun set(value: T) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun get(): T = data.first()
}

internal fun DataStore<Preferences>.intPreference(
    name: String,
    defaultValue: Int = 0
) =
    Preference(this, intPreferencesKey(name), defaultValue)


internal fun DataStore<Preferences>.doublePreference(
    name: String,
    defaultValue: Double = .0
) =
    Preference(this, doublePreferencesKey(name), defaultValue)


internal fun DataStore<Preferences>.stringPreference(
    name: String,
    defaultValue: String = ""
) =
    Preference(this, stringPreferencesKey(name), defaultValue)

internal fun DataStore<Preferences>.booleanPreference(
    name: String,
    defaultValue: Boolean = false
) =
    Preference(this, booleanPreferencesKey(name), defaultValue)

internal fun DataStore<Preferences>.floatPreference(
    name: String,
    defaultValue: Float = .0f
) =
    Preference(this, floatPreferencesKey(name), defaultValue)

internal fun DataStore<Preferences>.longPreference(
    name: String,
    defaultValue: Long = 0
) =
    Preference(this, longPreferencesKey(name), defaultValue)

internal fun DataStore<Preferences>.stringSetPreference(
    name: String,
    defaultValue: Set<String> = emptySet()
) =
    Preference(this, stringSetPreferencesKey(name), defaultValue)

internal fun DataStore<Preferences>.byteArrayPreference(
    name: String,
    defaultValue: ByteArray = byteArrayOf()
) =
    Preference(this, byteArrayPreferencesKey(name), defaultValue)