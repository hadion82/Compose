package com.example.test.data.character

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.repository.CharacterRepository
import com.example.model.CharacterData
import com.example.test.data.cached.CachedCharacterData
import com.example.test.data.paging.FakeCharacterPagingSource
import kotlinx.coroutines.flow.Flow

class FakeCharacterRepository(
    private val cachedCharacterData: CachedCharacterData = CachedCharacterData(),
    private val remoteMediator: FakeRemoteMediator = FakeRemoteMediator()
): CharacterRepository {

    override suspend fun getIds(ids: List<Int>): List<Int> =
        listOf(cachedCharacterData.getData().id)

    @OptIn(ExperimentalPagingApi::class)
    override fun loadPagingData(): Flow<PagingData<CharacterData>> =
        Pager(
            config = PagingConfig(
                pageSize = CharacterRepository.PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = CharacterRepository.PREFETCH_DISTANCE
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = {
                FakeCharacterPagingSource(cachedCharacterData.getData())
            }
        ).flow

    override suspend fun getCharacterById(id: Int): CharacterData? =
        if(cachedCharacterData.getData().id == id) cachedCharacterData.getData() else null
}