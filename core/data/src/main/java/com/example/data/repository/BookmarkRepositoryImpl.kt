package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.datasource.local.BookmarkLocalDataSource
import com.example.data.datasource.local.BookmarkLocalDataSourceImpl
import com.example.data.mapper.BookMarkDataMapper
import com.example.data.model.BookmarkData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class BookmarkRepositoryImpl @Inject internal constructor(
    private val dataSource: BookmarkLocalDataSourceImpl,
    private val bookMarkDataMapper: BookMarkDataMapper
) : BookmarkRepository, BookmarkLocalDataSource by dataSource {

    override suspend fun addBookmark(id: Int) =
        dataSource.addBookmark(id)

    override suspend fun removeBookmark(id: Int) =
        dataSource.removeBookmark(id)

    override fun loadBookmarkIds(): Flow<List<Int>> = loadIds()

    override fun loadPagingBookmarks(): Flow<PagingData<BookmarkData>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 10
            ),

            pagingSourceFactory = {
                dataSource.loadPagingMarked()
            }
        ).flow.map { paging -> paging.map(bookMarkDataMapper)}
}