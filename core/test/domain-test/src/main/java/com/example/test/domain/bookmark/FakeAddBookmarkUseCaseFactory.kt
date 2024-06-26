package com.example.test.domain.bookmark

import com.example.domain.usecase.bookmark.AddBookmarkUseCase
import com.example.test.data.bookmark.FakeBookmarkRepository
import com.example.test.data.character.FakeCharacterRepository
import kotlinx.coroutines.CoroutineDispatcher

interface FakeAddBookmarkUseCaseFactory {

    companion object : (CoroutineDispatcher, FakeBookmarkRepository) -> AddBookmarkUseCase {
        override fun invoke(dispatcher: CoroutineDispatcher, repository: FakeBookmarkRepository): AddBookmarkUseCase =
            AddBookmarkUseCase(
                dispatcher, repository
            )
    }
}