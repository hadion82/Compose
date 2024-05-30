package com.example.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.datasource.local.BookmarkLocalDataSource
import com.example.data.datasource.local.BookmarkLocalDataSourceImpl
import com.example.data.mapper.BookMarkDataMapper
import com.example.data.mediator.CharacterMediator
import com.example.data.model.BookmarkData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class CharacterRepositoryImpl @Inject internal constructor(
    private val localDataSource: BookmarkLocalDataSource,
    private val characterMediator: CharacterMediator,
    private val bookmarkDataMapper: BookMarkDataMapper
) : CharacterRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun loadPagingData(): Flow<PagingData<BookmarkData>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = PREFETCH_DISTANCE
            ),
            remoteMediator = characterMediator,
            pagingSourceFactory = {
                localDataSource.loadPagingAll()
            }
        ).flow.map { paging -> paging.map(bookmarkDataMapper) }

    override suspend fun getCharacterById(id: Int): BookmarkData? =
        localDataSource.getBookmarksById(id)?.let(bookmarkDataMapper)

    companion object {
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 20
    }
}