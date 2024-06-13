package com.example.test.domain.character

import com.example.test.data.character.FakeCharacterRepository
import com.example.domain.mapper.DataToMarvelMapper
import com.example.domain.usecase.character.LoadCharacterUseCase

interface FakeLoadCharacterUseCaseFactory {
    companion object: () -> LoadCharacterUseCase {
        override fun invoke(): LoadCharacterUseCase =
            LoadCharacterUseCase(
                com.example.test.data.character.FakeCharacterRepository(),
                DataToMarvelMapper()
            )
    }
}