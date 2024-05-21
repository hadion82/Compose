package com.example.data.datasource.remote

import androidx.annotation.IntRange
import com.example.network.model.CharacterDataWrapper

internal interface CharacterRemoteDataSource {

    suspend fun getCharacters(
        @IntRange(from = 0) offset: Int,
        @IntRange(from = 0, to = 100) limit: Int
    ): CharacterDataWrapper
}