package com.example.test.domain.bookmark

import com.example.test.data.bookmark.FakeBookmarkRepository
import com.example.domain.usecase.bookmark.RemoveBookmarkUseCase
import kotlinx.coroutines.CoroutineDispatcher

interface FakeRemoveBookmarkUseCaseFactory {

    companion object : (CoroutineDispatcher) -> RemoveBookmarkUseCase {
        override fun invoke(dispatcher: CoroutineDispatcher): RemoveBookmarkUseCase =
            RemoveBookmarkUseCase(
                dispatcher, com.example.test.data.bookmark.FakeBookmarkRepository()
            )
    }
}