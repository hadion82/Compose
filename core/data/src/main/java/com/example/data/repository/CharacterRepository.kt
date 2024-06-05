package com.example.data.repository

import androidx.annotation.IntRange
import androidx.paging.PagingData
import com.example.data.model.CharacterData
import com.example.database.model.CharacterEntity
import com.example.database.model.CharacterUpdatingEntity
import com.example.network.model.CharacterDataWrapper
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getIds(ids: List<Int>): List<Int>
    @Throws(Exception::class)
    fun loadPagingData(): Flow<PagingData<CharacterData>>

    suspend fun getCharacterById(id: Int): CharacterData?

    suspend fun getCharacters(
        @IntRange(from = 0) offset: Int,
        @IntRange(from = 0, to = 100) limit: Int
    ): CharacterDataWrapper
}