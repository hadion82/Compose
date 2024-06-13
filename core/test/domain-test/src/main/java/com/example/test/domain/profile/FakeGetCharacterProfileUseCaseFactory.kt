package com.example.test.domain.profile

import com.example.test.data.character.FakeCharacterRepository
import com.example.domain.mapper.DataToMarvelMapper
import com.example.domain.usecase.profile.GetCharacterProfileUseCase
import kotlinx.coroutines.CoroutineDispatcher

interface FakeGetCharacterProfileUseCaseFactory {

    companion object: (CoroutineDispatcher) -> GetCharacterProfileUseCase {
        override fun invoke(dispatcher: CoroutineDispatcher): GetCharacterProfileUseCase =
            GetCharacterProfileUseCase(
                dispatcher,
                com.example.test.data.character.FakeCharacterRepository(), DataToMarvelMapper()
            )
    }
}