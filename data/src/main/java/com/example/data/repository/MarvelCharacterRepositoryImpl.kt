package com.example.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.datasource.local.BookmarkLocalDataSourceImpl
import com.example.data.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarvelCharacterRepositoryImpl @Inject internal constructor(
    private val localDataSource: BookmarkLocalDataSourceImpl,
    private val characterMediator: CharacterMediator
) : CharacterRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun loadPagingData(): Flow<PagingData<BookmarkEntity>> =
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
        ).flow

    companion object {
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 20
    }
}