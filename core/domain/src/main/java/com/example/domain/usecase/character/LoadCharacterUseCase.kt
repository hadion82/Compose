package com.example.domain.usecase.character

import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.repository.CharacterRepository
import com.example.domain.mapper.DataToMarvelMapper
import com.example.model.MarvelCharacter
import com.example.shared.interaction.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val mapper: DataToMarvelMapper
) : UseCase<Unit, Flow<PagingData<MarvelCharacter>>> {

    override fun execute(params: Unit): Flow<PagingData<MarvelCharacter>> =
        characterRepository.loadPagingData()
            .map { paging -> paging.map(mapper) }
}