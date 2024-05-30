package com.example.data.di

import com.example.data.datasource.local.BookmarkLocalDataSource
import com.example.data.datasource.local.BookmarkLocalDataSourceImpl
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
        impl: BookmarkLocalDataSourceImpl
    ): BookmarkLocalDataSource
}