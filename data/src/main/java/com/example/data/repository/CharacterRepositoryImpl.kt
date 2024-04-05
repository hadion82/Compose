package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.datasource.local.BookmarkLocalDataSourceImpl
import com.example.data.datasource.remote.CharacterRemoteDataSourceImpl
import com.example.data.entity.BookmarkEntity
import com.example.data.mapper.PagingCharacterMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject internal constructor(
    private val remoteDataSource: CharacterRemoteDataSourceImpl,
    private val localDataSource: BookmarkLocalDataSourceImpl,
    private val mapper: PagingCharacterMapper
) : CharacterRepository {
    override fun loadPagingData(): Flow<PagingData<BookmarkEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = PREFETCH_DISTANCE
            ),
            initialKey = 0,
            pagingSourceFactory = {
                CharacterPagingSource(
                    remoteDataSource, localDataSource, mapper
                )
            }
        ).flow

    companion object {
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 15
    }
}