package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.LocalDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabaseModule(@ApplicationContext context: Context): LocalDataBase =
        Room.databaseBuilder(
            context,
            LocalDataBase::class.java,
            "database.db"
        ).build()
}