package com.example.data.di

import com.example.data.datasource.local.CharacterLocalDataSource
import com.example.data.datasource.local.CharacterLocalDataSourceImpl
import com.example.data.datasource.remote.CharacterRemoteDataSource
import com.example.data.datasource.remote.CharacterRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceBindModule {

    @Singleton
    @Binds
    internal abstract fun bindCharacterRemoteDataSource(
        impl: CharacterRemoteDataSourceImpl
    ): CharacterRemoteDataSource

    @Singleton
    @Binds
    internal abstract fun bindBookmarkLocalDataSource(
        impl: CharacterLocalDataSourceImpl
    ): CharacterLocalDataSource
}