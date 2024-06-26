package com.example.test.domain.bookmark

import com.example.test.data.bookmark.FakeBookmarkRepository
import com.example.domain.usecase.bookmark.RemoveBookmarkUseCase
import com.example.test.data.character.FakeCharacterRepository
import kotlinx.coroutines.CoroutineDispatcher

interface FakeRemoveBookmarkUseCaseFactory {

    companion object : (CoroutineDispatcher, FakeBookmarkRepository) -> RemoveBookmarkUseCase {
        override fun invoke(
            dispatcher: CoroutineDispatcher,
            repository: FakeBookmarkRepository
        ): RemoveBookmarkUseCase =
            RemoveBookmarkUseCase(
                dispatcher, repository
            )
    }
}