package com.example.data.di

import com.example.data.datasource.local.CharacterLocalDataSource
import com.example.data.datasource.local.CharacterLocalDataSourceImpl
import com.example.data.datasource.remote.CharacterRemoteDataSource
import com.example.data.datasource.remote.CharacterRemoteDataSourceImpl
import com.example.data.media.ImageDownloader
import com.example.data.media.ImageDownloaderImpl
import com.example.data.media.MediaStore
import com.example.data.media.MediaStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class MediaBindModule {

    @Singleton
    @Binds
    internal abstract fun bindImageDownloader(
        impl: ImageDownloaderImpl
    ): ImageDownloader

    @Singleton
    @Binds
    internal abstract fun bindMediaStore(
        impl: MediaStoreImpl
    ): MediaStore
}