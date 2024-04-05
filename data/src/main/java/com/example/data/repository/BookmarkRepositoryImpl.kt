package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.datasource.local.BookmarkLocalDataSource
import com.example.data.datasource.local.BookmarkLocalDataSourceImpl
import com.example.data.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject internal constructor(
    private val dataSource: BookmarkLocalDataSourceImpl
) : BookmarkRepository, BookmarkLocalDataSource by dataSource {

    override suspend fun addBookmark(id: Int) =
        dataSource.addBookmark(id)

    override suspend fun removeBookmark(id: Int) =
        dataSource.removeBookmark(id)

    override fun loadBookmarkIds(): Flow<List<Int>> = loadIds()

    override fun loadPagingBookmarks(): Flow<PagingData<BookmarkEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 10
            ),

            pagingSourceFactory = {
                dataSource.loadPagingMakred()
            }
        ).flow/*.map { data -> data.map { entity -> entity.decrypted() } }*/
}