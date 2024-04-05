package com.example.domain.usecase.character

import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.model.CharacterItem
import com.example.data.repository.CharacterRepository
import com.example.domain.mapper.CharacterItemMapper
import com.example.shared.interaction.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoadCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val mapper: CharacterItemMapper
) : UseCase<Unit, Flow<PagingData<CharacterItem>>> {

    override fun execute(params: Unit) =
        characterRepository.loadPagingData()
            .map { it.map(mapper) }
}