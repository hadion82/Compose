package com.example.home

import com.example.domain.usecase.character.LoadCharacterUseCase
import com.example.domain.usecase.thumbnail.SaveThumbnailUseCase
import com.example.shared.hanlder.AbstractEffectHandler
import javax.inject.Inject

internal class HomeEffectHandler @Inject constructor(
    dispatcher: HomeActionDispatcher,
    private val loadCharacterUseCase: LoadCharacterUseCase,
    private val saveThumbnailUseCase: SaveThumbnailUseCase
) : AbstractEffectHandler<Intention.Effect>(),
    HomeActionDispatcher by dispatcher {

    override suspend fun execute(intent: Intention.Effect) {
        when (intent) {
            is Intention.Effect.LoadCharacters -> {
                loadCharacterUseCase(Unit)
                    .onSuccess { dispatch(Action.LoadPagingData(it)) }
                    .onFailure { dispatch(Action.Message.FailedToLoadData) }
            }
            is Intention.Effect.SaveThumbnail -> {
                saveThumbnailUseCase(SaveThumbnailUseCase.Params(intent.path))
                    .onSuccess { dispatch(Action.Message.SuccessToSaveImage) }
                    .onFailure { dispatch(Action.Message.FailedToSaveImage) }
            }
        }
    }
}