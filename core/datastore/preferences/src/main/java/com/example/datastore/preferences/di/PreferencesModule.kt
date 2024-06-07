package com.example.datastore.preferences.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.datastore.preferences.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object PreferencesModule {

    @LocalPreferences
    @Singleton
    @Provides
    fun providesPreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.dataStore
}