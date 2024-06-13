package com.example.domain.usecase.profile

import com.example.data.repository.CharacterRepository
import com.example.domain.mapper.DataToMarvelMapper
import com.example.model.MarvelCharacter
import com.example.shared.di.IoDispatcher
import com.example.shared.interaction.SuspendingUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetCharacterProfileUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val characterRepository: CharacterRepository,
    private val mapper: DataToMarvelMapper
) : SuspendingUseCase<GetCharacterProfileUseCase.Params, MarvelCharacter>(dispatcher) {

    override suspend fun execute(params: Params): MarvelCharacter {
        return characterRepository.getCharacterById(params.id)?.let(mapper)
            ?: throw NoSuchElementException("No matching object found for id")
    }

    data class Params(val id: Int)
}