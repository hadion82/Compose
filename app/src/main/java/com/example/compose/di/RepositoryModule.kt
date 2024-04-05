package com.example.compose.di

import com.example.data.datasource.stream.ImageStreamRepository
import com.example.data.datasource.stream.ImageStreamRepositoryImpl
import com.example.data.repository.CharacterRepository
import com.example.data.repository.BookmarkRepository
import com.example.data.repository.BookmarkRepositoryImpl
import com.example.data.repository.MarvelCharacterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideCharacterRepository(
        characterRepository: MarvelCharacterRepositoryImpl
    ): CharacterRepository

    @Singleton
    @Binds
    abstract fun provideBookmarkRepository(
        bookmarkRepository: BookmarkRepositoryImpl
    ): BookmarkRepository

    @Singleton
    @Binds
    abstract fun provideImageStreamRepository(
        imageStreamRepository: ImageStreamRepositoryImpl
    ): ImageStreamRepository
}