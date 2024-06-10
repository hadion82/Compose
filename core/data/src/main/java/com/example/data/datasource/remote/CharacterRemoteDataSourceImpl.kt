package com.example.data.datasource.remote
import com.example.data.mapper.RemoteToDataMapper
import com.example.data.model.CharacterResponse
import com.example.network.service.MarvelService
import javax.inject.Inject

internal class CharacterRemoteDataSourceImpl @Inject constructor(
    private val marvelService: MarvelService,
    private val remoteToResponseMapper: RemoteToDataMapper
) : CharacterRemoteDataSource {

    override suspend fun getCharacters(offset: Int, limit: Int): CharacterResponse =
        with(marvelService.getCharacters(offset, limit)) {
            CharacterResponse(
                code = code ?: 200,
                offset = data?.offset ?: 0,
                limit = data?.limit ?: 0,
                total = data?.total ?: 0,
                results = data?.results?.map(remoteToResponseMapper) ?: emptyList(),
            )
        }
}