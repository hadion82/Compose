package com.example.test.domain.image

import android.content.Context
import com.example.domain.usecase.thumbnail.SaveThumbnailUseCase
import com.example.test.data.media.FakeImageRepository
import kotlinx.coroutines.CoroutineDispatcher

interface FakeSaveThumbnailUseCaseFactory {

    companion object : (Context, CoroutineDispatcher) -> SaveThumbnailUseCase {
        override fun invoke(
            context: Context,
            dispatcher: CoroutineDispatcher
        ): SaveThumbnailUseCase =
            SaveThumbnailUseCase(
                context, dispatcher, FakeImageRepository()
            )
    }
}