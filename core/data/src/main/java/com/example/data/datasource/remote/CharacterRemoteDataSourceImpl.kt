package com.example.data.datasource.remote
import com.example.network.model.CharacterDataWrapper
import com.example.network.service.MarvelService
import javax.inject.Inject

internal class CharacterRemoteDataSourceImpl @Inject constructor(
    private val marvelService: MarvelService
) : CharacterRemoteDataSource {

    override suspend fun getCharacters(offset: Int, limit: Int): CharacterDataWrapper =
        marvelService.getCharacters(offset, limit)
}