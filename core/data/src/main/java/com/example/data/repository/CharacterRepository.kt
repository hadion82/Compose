package com.example.data.repository

import androidx.paging.PagingData
import com.example.model.CharacterData
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getIds(ids: List<Int>): List<Int>
    @Throws(Exception::class)
    fun loadPagingData(): Flow<PagingData<CharacterData>>

    suspend fun getCharacterById(id: Int): CharacterData?

    companion object {
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 20
    }
}