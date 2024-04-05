package com.example.data.datasource.remote
import com.example.data.api.MarvelApi
import com.example.data.api.MarvelApiClient
import com.example.data.model.CharacterDataWrapper
import javax.inject.Inject

internal class CharacterRemoteDataSourceImpl @Inject constructor(
    encapsulation: MarvelApiClient
) : CharacterRemoteDataSource {

    private val service: MarvelApi = encapsulation()

    override suspend fun getCharacters(offset: Int, limit: Int): CharacterDataWrapper =
        service.getCharacters(offset, limit)
}