package com.example.data.di

import com.example.data.datasource.local.CharacterLocalDataSource
import com.example.data.datasource.local.CharacterLocalDataSourceImpl
import com.example.data.datasource.remote.CharacterRemoteDataSource
import com.example.data.datasource.remote.CharacterRemoteDataSourceImpl
import com.example.fio.media.ImageDownloader
import com.example.fio.media.ImageDownloaderImpl
import com.example.fio.media.MediaStore
import com.example.fio.media.MediaStoreImpl
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
        impl: com.example.fio.media.ImageDownloaderImpl
    ): com.example.fio.media.ImageDownloader

    @Singleton
    @Binds
    internal abstract fun bindMediaStore(
        impl: com.example.fio.media.MediaStoreImpl
    ): com.example.fio.media.MediaStore
}