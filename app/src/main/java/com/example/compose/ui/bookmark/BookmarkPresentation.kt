package com.example.compose.ui.bookmark

sealed interface Action {

    sealed interface Message : Action {
        data object FailedToRemoveBookmark : Message
        data object SuccessToSaveImage : Message
        data object FailedToSaveImage : Message
        data object FailedToLoadData : Message
    }
}
