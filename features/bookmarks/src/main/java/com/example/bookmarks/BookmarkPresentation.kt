package com.example.bookmarks

sealed interface Action {

    sealed interface Message : Action {
        data object FailedToRemoveBookmark : Message
        data object SuccessToSaveImage : Message
        data object FailedToSaveImage : Message
        data object FailedToLoadData : Message
    }
}
