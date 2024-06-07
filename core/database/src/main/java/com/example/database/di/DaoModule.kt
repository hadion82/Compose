package com.example.database.di

import com.example.database.LocalDataBase
import com.example.database.dao.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun providesBookmarkDao(
        dataBase: LocalDataBase
    ): CharacterDao = dataBase.characterDao()
}