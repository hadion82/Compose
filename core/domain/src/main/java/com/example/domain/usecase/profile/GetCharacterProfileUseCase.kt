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
        val item = characterRepository.getCharacterById(params.id)
            ?: throw NoSuchElementException("No matching object found for id")
        return item.let(mapper)
    }

    data class Params(val id: Int)
}