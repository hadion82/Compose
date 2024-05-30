package com.example.data.di

import com.example.data.repository.ImageStreamRepository
import com.example.data.repository.ImageStreamRepositoryImpl
import com.example.data.repository.CharacterRepository
import com.example.data.repository.BookmarkRepository
import com.example.data.repository.BookmarkRepositoryImpl
import com.example.data.repository.CharacterRepositoryImpl
import com.example.data.repository.SyncRepository
import com.example.data.repository.SyncRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCharacterRepository(
        characterRepository: CharacterRepositoryImpl
    ): CharacterRepository

    @Singleton
    @Binds
    abstract fun bindsBookmarkRepository(
        bookmarkRepository: BookmarkRepositoryImpl
    ): BookmarkRepository

    @Singleton
    @Binds
    abstract fun bindsImageStreamRepository(
        imageStreamRepository: ImageStreamRepositoryImpl
    ): ImageStreamRepository

    @Singleton
    @Binds
    abstract fun bindsSyncRepository(
        syncRepository: SyncRepositoryImpl
    ): SyncRepository
}