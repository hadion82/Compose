package com.example.domain.usecase.profile

import com.example.data.repository.CharacterRepository
import com.example.domain.mapper.MarvelCharacterMapper
import com.example.model.MarvelCharacter
import com.example.shared.di.IoDispatcher
import com.example.shared.interaction.SuspendingUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetCharacterProfileUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val characterRepository: CharacterRepository,
    private val mapper: MarvelCharacterMapper
) : SuspendingUseCase<GetCharacterProfileUseCase.Params, MarvelCharacter>(dispatcher) {

    override suspend fun execute(params: Params): MarvelCharacter =
        characterRepository.getCharacterById(params.id)?.let(mapper) ?:
        throw NoSuchElementException("Unknown character id value")

    data class Params(val id: Int)
}