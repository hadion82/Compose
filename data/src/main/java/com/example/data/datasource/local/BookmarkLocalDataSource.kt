package com.example.data.datasource.local

import androidx.annotation.IntRange
import androidx.paging.PagingSource
import com.example.data.entity.BookmarkEntity
import com.example.data.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface BookmarkLocalDataSource {

    suspend fun insert(
        vararg values: BookmarkEntity
    )

    suspend fun update(
        values: List<MarvelCharacter>
    )

    suspend fun getBookmarksById(ids: List<Int>): List<BookmarkEntity>

    suspend fun getIds(ids: List<Int>): List<Int>

    fun loadIds(): Flow<List<Int>>

    fun loadPagingMakred(): PagingSource<Int, BookmarkEntity>

    fun loadPagingAll(): PagingSource<Int, BookmarkEntity>

    suspend fun addBookmark(
        @IntRange(from = 0) id: Int
    )

    suspend fun removeBookmark(
        @IntRange(from = 0) id: Int
    )
}