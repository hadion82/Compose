package com.example.data.datasorce

import com.example.data.datasource.remote.CharacterRemoteDataSource
import com.example.data.model.CharacterResponse
import com.example.network.model.CharacterDataWrapper
import java.net.UnknownHostException

class CharacterRemoteTestingDataExceptionSource : CharacterRemoteDataSource {
    override suspend fun getCharacters(offset: Int, limit: Int): CharacterResponse =
        throw UnknownHostException()

}