package com.example.data.mapper

import com.example.data.model.CharacterResponse
import com.example.network.model.CharacterDataWrapper
import com.example.shared.mapper.Mapper
import javax.inject.Inject

class RemoteToResponseMapper @Inject constructor(
    private val remoteToResponseMapper: RemoteToDataMapper
): Mapper<CharacterDataWrapper, CharacterResponse> {
    override fun invoke(data: CharacterDataWrapper): CharacterResponse =
        CharacterResponse(
            code = data.code ?: 200,
            offset = data.data?.offset ?: 0,
            limit = data.data?.limit ?: 0,
            total = data.data?.total ?: 0,
            results = data.data?.results?.map(remoteToResponseMapper) ?: emptyList(),
        )
}