package com.example.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.datasource.local.CharacterLocalDataSource
import com.example.data.datasource.remote.CharacterRemoteDataSource
import com.example.data.mapper.CharacterDataMapper
import com.example.data.mediator.CharacterMediator
import com.example.data.model.CharacterData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class CharacterRepositoryImpl @Inject internal constructor(
    private val localDataSource: CharacterLocalDataSource,
    private val remoteDataSource: CharacterRemoteDataSource,
    private val characterMediator: CharacterMediator,
    private val characterDataMapper: CharacterDataMapper,
) : CharacterRepository,
    CharacterLocalDataSource by localDataSource,
    CharacterRemoteDataSource by remoteDataSource {
    @OptIn(ExperimentalPagingApi::class)
    override fun loadPagingData(): Flow<PagingData<CharacterData>> =
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
        ).flow.map { paging -> paging.map(characterDataMapper) }

    override suspend fun getCharacterById(id: Int): CharacterData? =
        localDataSource.getCharactersById(id)?.let(characterDataMapper)

    companion object {
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 20
    }
}