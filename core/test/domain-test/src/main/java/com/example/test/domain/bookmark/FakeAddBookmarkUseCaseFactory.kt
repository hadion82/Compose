package com.example.test.domain.bookmark

import com.example.test.data.bookmark.FakeBookmarkRepository
import com.example.domain.usecase.bookmark.AddBookmarkUseCase
import kotlinx.coroutines.CoroutineDispatcher

interface FakeAddBookmarkUseCaseFactory {

    companion object : (CoroutineDispatcher) -> AddBookmarkUseCase {
        override fun invoke(dispatcher: CoroutineDispatcher): AddBookmarkUseCase =
            AddBookmarkUseCase(
                dispatcher, com.example.test.data.bookmark.FakeBookmarkRepository()
            )
    }
}