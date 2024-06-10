package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.datasource.local.CharacterLocalDataSource
import com.example.data.mapper.EntityToDataMapper
import com.example.model.CharacterData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class BookmarkRepositoryImpl @Inject internal constructor(
    private val dataSource: CharacterLocalDataSource,
    private val bookMarkDataMapper: EntityToDataMapper
) : BookmarkRepository, CharacterLocalDataSource by dataSource {

    override suspend fun addBookmark(id: Int) =
        dataSource.addBookmark(id)

    override suspend fun removeBookmark(id: Int) =
        dataSource.removeBookmark(id)

    override fun loadPagingBookmarks(): Flow<PagingData<com.example.model.CharacterData>> =
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