package com.example.data.repository

import androidx.annotation.IntRange
import com.example.model.CharacterData
import com.example.data.model.CharacterResponse

interface SyncRepository {

    suspend fun getCharacters(
        @IntRange(from = 0) offset: Int,
        @IntRange(from = 0, to = 100) limit: Int
    ): CharacterResponse

    suspend fun updateCharacters(results: List<CharacterData>)
}