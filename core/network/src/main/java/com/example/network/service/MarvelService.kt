package com.example.network.service

import com.example.network.model.CharacterDataWrapper
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query(value = "offset") offset: Int,
        @androidx.annotation.IntRange(from = 0, to = 100)
        @Query(value = "limit") limit: Int
    ): CharacterDataWrapper
}