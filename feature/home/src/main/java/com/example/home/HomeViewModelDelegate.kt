package com.example.home

import com.example.ui.viewmodel.ViewModelDelegate
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

internal interface HomeViewModelDelegate : ViewModelDelegate<Intention, Action>

internal class HomeViewModelDelegateImpl @Inject constructor() :
    HomeViewModelDelegate {
    override val intents: MutableSharedFlow<Intention> = MutableSharedFlow()

    override suspend fun processIntent(intent: Intention) = intents.emit(intent)
}