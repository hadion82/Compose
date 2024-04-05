package com.example.compose.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shared.di.DefaultDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @DefaultDispatcher processDispatcher: CoroutineDispatcher,
    mainViewModelDelegate: MainViewModelDelegate,
    processor: MainProcessor,
    dispatcher: MainActionDispatcher,
    reducer: MainActionReducer,
) : ViewModel(),
    MainViewModelDelegate by mainViewModelDelegate,
    MainProcessor by processor,
    MainActionDispatcher by dispatcher,
    MainActionReducer by reducer {

    init {
        intents.onEach(::process)
            .flowOn(processDispatcher)
            .launchIn(viewModelScope)

        actions().onEach(::reduce)
            .launchIn(viewModelScope)
    }
}