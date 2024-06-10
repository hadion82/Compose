package com.example.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.datasource.local.CharacterLocalDataSource
import com.example.data.datasource.remote.CharacterRemoteDataSource
import com.example.data.mapper.EntityToDataMapper
import com.example.data.mediator.CharacterMediator
import com.example.model.CharacterData
import com.example.data.repository.CharacterRepository.Companion.PAGE_SIZE
import com.example.data.repository.CharacterRepository.Companion.PREFETCH_DISTANCE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class CharacterRepositoryImpl @Inject internal constructor(
    private val localDataSource: CharacterLocalDataSource,
    private val remoteDataSource: CharacterRemoteDataSource,
    private val characterMediator: CharacterMediator,
    private val characterDataMapper: EntityToDataMapper,
) : CharacterRepository,
    CharacterLocalDataSource by localDataSource,
    CharacterRemoteDataSource by remoteDataSource {
    @OptIn(ExperimentalPagingApi::class)
    override fun loadPagingData(): Flow<PagingData<com.example.model.CharacterData>> =
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

    override suspend fun getCharacterById(id: Int): com.example.model.CharacterData? =
        localDataSource.getCharactersById(id)?.let(characterDataMapper)


}