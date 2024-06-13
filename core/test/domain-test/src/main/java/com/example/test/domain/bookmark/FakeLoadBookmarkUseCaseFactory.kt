package com.example.test.domain.bookmark

import com.example.test.data.bookmark.FakeBookmarkRepository
import com.example.domain.mapper.DataToMarvelMapper
import com.example.domain.usecase.bookmark.LoadBookmarkUseCase

interface FakeLoadBookmarkUseCaseFactory {

    companion object : () -> LoadBookmarkUseCase {
        override fun invoke(): LoadBookmarkUseCase =
            LoadBookmarkUseCase(com.example.test.data.bookmark.FakeBookmarkRepository(), DataToMarvelMapper())
    }
}