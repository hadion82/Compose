package com.example.compose.ui.main

import com.example.domain.usecase.bookmark.AddBookmarkUseCase
import com.example.domain.usecase.bookmark.RemoveBookmarkUseCase
import com.example.shared.hanlder.AbstractActionHandler
import javax.inject.Inject

class MainEventHandler @Inject constructor(
    dispatcher: MainActionDispatcher,
    private val addBookmarkUseCase: AddBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase
) : AbstractActionHandler<Intention.Event>(),
    MainActionDispatcher by dispatcher {

    override suspend fun execute(intent: Intention.Event) {
        when (intent) {
            is Intention.Event.AddBookmark ->
                addBookmarkUseCase(AddBookmarkUseCase.Params(intent.id))
                    .onFailure { dispatch(Action.Message.FailedToBookmark) }
            is Intention.Event.RemoveBookmark ->
                removeBookmarkUseCase(RemoveBookmarkUseCase.Params(intent.id))
                    .onFailure { dispatch(Action.Message.FailedToDeleteBookmark) }
        }
    }
}