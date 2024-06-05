package com.example.data.repository

import androidx.annotation.IntRange
import com.example.network.model.Character
import com.example.network.model.CharacterDataWrapper

interface SyncRepository {

    suspend fun getCharacters(
        @IntRange(from = 0) offset: Int,
        @IntRange(from = 0, to = 100) limit: Int
    ): CharacterDataWrapper

    suspend fun syncCharacters(results: List<Character>)
}