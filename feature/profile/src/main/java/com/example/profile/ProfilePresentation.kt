package com.example.profile

internal sealed interface Action {

    sealed interface Message : Action {
        data object FailedToLoadData : Message
    }
}
