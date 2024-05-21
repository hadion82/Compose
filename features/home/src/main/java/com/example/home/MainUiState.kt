package com.example.home

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import com.example.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface UiState {
    val pagingData: StateFlow<Flow<PagingData<MarvelCharacter>>?>
    val message: StateFlow<Action.Message?>
    val navigation: StateFlow<Action.Navigation?>
}

class MutableMainUiState(
    private val _pagingData: MutableStateFlow<Flow<PagingData<MarvelCharacter>>?>,
    private val _message: MutableStateFlow<Action.Message?>,
    private val _navigation: MutableStateFlow<Action.Navigation?>
) : UiState {
    companion object {
        fun idle() = MutableMainUiState(
            MutableStateFlow(null),
            MutableStateFlow(null),
            MutableStateFlow(null)
        )
    }

    override val pagingData: StateFlow<Flow<PagingData<MarvelCharacter>>?>
        get() = _pagingData
    override val message: StateFlow<Action.Message?>
        get() = _message
    override val navigation: StateFlow<Action.Navigation?>
        get() = _navigation

    suspend fun setPagingData(pagingData: Flow<PagingData<MarvelCharacter>>) {
        _pagingData.emit(pagingData)
    }

    suspend fun setMessage(message: Action.Message) {
        _message.emit(message)
    }

    suspend fun setNavigation(action: Action.Navigation) {
        _navigation.emit(action)
    }
}

//XML UI State
open class MainUiState(
    val pagingData: State<Flow<PagingData<MarvelCharacter>>?>,
    val message: State<Action.Message?>,
    val navigation: State<Action.Navigation?>
)

//Compose UI State
class MainComposableUiState(
    pagingData: State<Flow<PagingData<MarvelCharacter>>?>,
    message: State<Action.Message?>,
    navigation: State<Action.Navigation?>,
    val snackBarHostState: SnackbarHostState
) : MainUiState(pagingData, message, navigation)

@Composable
fun ComposeMainUiState(uiState: UiState): MainComposableUiState {
    val pagingDataState = uiState.pagingData.collectAsStateWithLifecycle()
    val messageState = uiState.message.collectAsStateWithLifecycle()
    val navigationState = uiState.navigation.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    return remember {
        MainComposableUiState(pagingDataState, messageState, navigationState, snackBarHostState)
    }
}