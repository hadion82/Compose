package com.example.test.domain.character

import com.example.test.data.sync.FakeSyncRepository
import com.example.domain.usecase.character.SyncCharacterUseCase
import kotlinx.coroutines.CoroutineDispatcher

interface FakeSyncCharacterUseCaseFactory {

    companion object: (CoroutineDispatcher) -> SyncCharacterUseCase {
        override fun invoke(dispatcher: CoroutineDispatcher): SyncCharacterUseCase =
            SyncCharacterUseCase(
                dispatcher, com.example.test.data.sync.FakeSyncRepository()
            )
    }
}