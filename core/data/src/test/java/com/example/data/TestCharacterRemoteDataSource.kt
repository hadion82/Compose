package com.example.data

import com.example.data.datasource.remote.CharacterRemoteDataSource
import com.example.network.model.CharacterDataWrapper
import com.example.testing.data.FakeMarvelCharacterData

class TestCharacterRemoteDataSource : CharacterRemoteDataSource {
    override suspend fun getCharacters(offset: Int, limit: Int): CharacterDataWrapper =
        FakeMarvelCharacterData.createCharacterData(offset, limit)

}