package com.example.domain.data.character

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.model.CharacterData
import com.example.data.repository.CharacterRepository
import com.example.domain.data.FakeCharacterPagingSource
import kotlinx.coroutines.flow.Flow

class FakeCharacterRepository: CharacterRepository {

    companion object {
        const val TEST_ID = 1
    }

    private val testCharacterData = CharacterData(
        id = TEST_ID,
        name = "name1",
        description = "description1",
        thumbnail = null,
        urlCount = 0,
        comicCount = 0,
        storyCount = 0,
        eventCount = 0,
        seriesCount = 0,
        mark = false
    )

    private var cachedCharacterData = testCharacterData

    override suspend fun getIds(ids: List<Int>): List<Int> =
        listOf(cachedCharacterData.id)

    override fun loadPagingData(): Flow<PagingData<CharacterData>> =
        Pager(
            config = PagingConfig(
                pageSize = CharacterRepository.PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = CharacterRepository.PREFETCH_DISTANCE
            ),
            pagingSourceFactory = {
                FakeCharacterPagingSource(cachedCharacterData)
            }
        ).flow

    override suspend fun getCharacterById(id: Int): CharacterData? =
        cachedCharacterData
}