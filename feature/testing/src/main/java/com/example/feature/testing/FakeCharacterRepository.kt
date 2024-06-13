package com.example.feature.testing

import com.example.data.repository.CharacterRepository
import com.example.model.CharacterData
import kotlinx.coroutines.flow.Flow

class FakeCharacterRepository: CharacterRepository {

    override suspend fun getIds(ids: List<Int>): List<Int> {
        TODO("Not yet implemented")
    }

    override fun loadPagingData(): Flow<androidx.paging.PagingData<CharacterData>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacterById(id: Int): CharacterData? {
        TODO("Not yet implemented")
    }
}