package com.example.data.api

import com.example.data.model.CharacterDataWrapper
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query(value = "offset") offset: Int,
        @androidx.annotation.IntRange(from = 0, to = 100)
        @Query(value = "limit") limit: Int
    ): CharacterDataWrapper
}