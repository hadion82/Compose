package com.example.database.di

import com.example.database.LocalDataBase
import com.example.database.dao.BookmarkDao
import com.example.database.dao.PagingKeyDao
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
    ): BookmarkDao = dataBase.bookmarkDao()

    @Provides
    fun providesPagingKeyDao(
        dataBase: LocalDataBase
    ): PagingKeyDao = dataBase.pagingKeyDao()
}