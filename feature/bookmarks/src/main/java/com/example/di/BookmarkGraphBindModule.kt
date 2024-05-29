package com.example.di

import com.example.bookmarks.BookmarkGraphImpl
import com.example.graph.BookmarkGraph
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class BookmarkGraphBindModule {
    @Binds
    abstract fun bindBookmarkGraph(impl: BookmarkGraphImpl): BookmarkGraph
}