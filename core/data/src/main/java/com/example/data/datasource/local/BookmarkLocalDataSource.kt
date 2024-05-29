package com.example.data.datasource.local

import androidx.annotation.IntRange
import androidx.paging.PagingSource
import com.example.database.model.BookmarkEntity
import com.example.database.model.CharacterEntity
import kotlinx.coroutines.flow.Flow

internal interface BookmarkLocalDataSource {

    suspend fun insert(
        vararg values: BookmarkEntity
    )

    suspend fun update(
        values: List<CharacterEntity>
    )

    suspend fun getBookmarksById(ids: List<Int>): List<BookmarkEntity>
    suspend fun getBookmarksById(id: Int): BookmarkEntity?

    suspend fun getIds(ids: List<Int>): List<Int>

    fun loadIds(): Flow<List<Int>>

    fun loadPagingMarked(): PagingSource<Int, BookmarkEntity>

    fun loadPagingAll(): PagingSource<Int, BookmarkEntity>

    suspend fun addBookmark(
        @IntRange(from = 0) id: Int
    )

    suspend fun removeBookmark(
        @IntRange(from = 0) id: Int
    )
}