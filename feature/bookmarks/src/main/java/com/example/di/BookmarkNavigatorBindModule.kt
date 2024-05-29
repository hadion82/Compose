package com.example.di

import com.example.bookmarks.BookmarkNavigatorImpl
import com.example.navigator.BookmarksNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class BookmarkNavigatorBindModule {
    @Binds
    abstract fun bindBookmarkNavigator(impl: BookmarkNavigatorImpl): BookmarksNavigator
}