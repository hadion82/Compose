package com.example.home

import com.example.ui.viewmodel.ViewModelDelegate
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

interface MainViewModelDelegate : ViewModelDelegate<Intention, Action>

class MainViewModelDelegateImpl @Inject constructor() :
    MainViewModelDelegate {
    override val intents: MutableSharedFlow<Intention> = MutableSharedFlow()

    override suspend fun processIntent(intent: Intention) = intents.emit(intent)
}