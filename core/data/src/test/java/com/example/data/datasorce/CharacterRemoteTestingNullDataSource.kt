package com.example.data.datasorce

import com.example.data.datasource.remote.CharacterRemoteDataSource
import com.example.network.model.CharacterDataWrapper
import com.example.testing.data.FakeMarvelCharacterData

class CharacterRemoteTestingNullDataSource : CharacterRemoteDataSource {
    override suspend fun getCharacters(offset: Int, limit: Int): CharacterDataWrapper =
        FakeMarvelCharacterData.createNullCharacterData(offset, limit)

}