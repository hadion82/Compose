package com.example.data.datasorce

import com.example.data.datasource.remote.CharacterRemoteDataSource
import com.example.data.mapper.RemoteToDataMapper
import com.example.data.mapper.RemoteToResponseMapper
import com.example.data.model.CharacterResponse
import com.example.testing.data.FakeMarvelCharacterData

class CharacterRemoteTestingDataSource(
    private val remoteToResponseMapper: RemoteToResponseMapper
) : CharacterRemoteDataSource {
    override suspend fun getCharacters(offset: Int, limit: Int): CharacterResponse =
        remoteToResponseMapper(
            FakeMarvelCharacterData.createCharacterData(offset, limit)
        )
}