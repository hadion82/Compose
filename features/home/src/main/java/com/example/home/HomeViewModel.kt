package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shared.di.DefaultDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @DefaultDispatcher processDispatcher: CoroutineDispatcher,
    mainViewModelDelegate: HomeViewModelDelegate,
    processor: HomeProcessor,
    dispatcher: HomeActionDispatcher,
    reducer: HomeActionReducer,
) : ViewModel(),
    HomeViewModelDelegate by mainViewModelDelegate,
    HomeProcessor by processor,
    HomeActionDispatcher by dispatcher,
    HomeActionReducer by reducer {

    init {
        intents.onEach(::process)
            .flowOn(processDispatcher)
            .launchIn(viewModelScope)

        actions().onEach(::reduce)
            .launchIn(viewModelScope)
    }
}