package com.example.compose.ui.main

import com.example.shared.dispatcher.ActionDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

interface MainActionDispatcher : ActionDispatcher<Action>

class MainActionDispatcherImpl @Inject constructor() : MainActionDispatcher {
    override val actions: MutableSharedFlow<Action> = MutableSharedFlow()

    override fun actions(): Flow<Action> = actions
}