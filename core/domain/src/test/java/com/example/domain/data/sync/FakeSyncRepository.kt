package com.example.domain.data.sync

import com.example.data.model.CharacterResponse
import com.example.model.CharacterData
import com.example.data.repository.SyncRepository

class FakeSyncRepository: SyncRepository {

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

    private var cachedCharacterData: List<CharacterData> = emptyList()

    override suspend fun getCharacters(
        offset: Int,
        limit: Int
    ): CharacterResponse =
        CharacterResponse(
            0, 1, 1, 1, listOf(testCharacterData)
        )

    override suspend fun updateCharacters(results: List<CharacterData>) {
        cachedCharacterData = results
    }

    fun getResults() = cachedCharacterData
}