package com.example.domain.usecase.character

import com.example.data.repository.SyncRepository
import com.example.shared.di.IoDispatcher
import com.example.shared.interaction.SuspendingUseCase
import kotlinx.coroutines.CoroutineDispatcher
import timber.log.Timber
import javax.inject.Inject

class SyncCharacterUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher,
    private val syncRepository: SyncRepository
) : SuspendingUseCase<SyncCharacterUseCase.Params, Unit>(dispatcher) {

    override suspend fun execute(params: Params) {
        val characters = syncRepository.getCharacters(params.offset, PAGE_LIMIT)
        syncRepository.updateCharacters(characters.results)
    }

    class Params(val offset: Int)

    companion object {
        const val PAGE_LIMIT = 20
    }
}