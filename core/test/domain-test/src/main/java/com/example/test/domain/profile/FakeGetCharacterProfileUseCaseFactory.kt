package com.example.test.domain.profile

import com.example.domain.mapper.DataToMarvelMapper
import com.example.domain.usecase.profile.GetCharacterProfileUseCase
import com.example.test.data.character.FakeCharacterRepository
import kotlinx.coroutines.CoroutineDispatcher

interface FakeGetCharacterProfileUseCaseFactory {

    companion object: (CoroutineDispatcher) -> GetCharacterProfileUseCase {
        override fun invoke(dispatcher: CoroutineDispatcher): GetCharacterProfileUseCase =
            GetCharacterProfileUseCase(
                dispatcher,
                FakeCharacterRepository(), DataToMarvelMapper()
            )
    }
}