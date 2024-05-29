package com.example.bookmarks

internal sealed interface Action {

    sealed interface Navigation: Action {
        data class MoveToDetail(val id: Int): Navigation
    }
    sealed interface Message : Action {
        data object FailedToRemoveBookmark : Message
        data object SuccessToSaveImage : Message
        data object FailedToSaveImage : Message
        data object FailedToLoadData : Message
    }
}
