package com.example.test.domain.character

import com.example.test.data.character.FakeCharacterRepository
import com.example.domain.mapper.DataToMarvelMapper
import com.example.domain.usecase.character.LoadCharacterUseCase

interface FakeLoadCharacterUseCaseFactory {
    companion object: (FakeCharacterRepository) -> LoadCharacterUseCase {
        override fun invoke(repository: FakeCharacterRepository): LoadCharacterUseCase =
            LoadCharacterUseCase(
                repository,
                DataToMarvelMapper()
            )
    }
}