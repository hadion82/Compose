package com.example.home

import com.example.shared.dispatcher.ActionDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

interface HomeActionDispatcher : ActionDispatcher<Action>

class HomeActionDispatcherImpl @Inject constructor() : HomeActionDispatcher {
    override val actions: MutableSharedFlow<Action> = MutableSharedFlow()

    override fun actions(): Flow<Action> = actions
}