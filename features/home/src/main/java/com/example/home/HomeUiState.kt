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
}

class MutableHomeUiState(
    private val _pagingData: MutableStateFlow<Flow<PagingData<MarvelCharacter>>?>,
    private val _message: MutableStateFlow<Action.Message?>
) : UiState {
    companion object {
        fun idle() = MutableHomeUiState(
            MutableStateFlow(null),
            MutableStateFlow(null)
        )
    }

    override val pagingData: StateFlow<Flow<PagingData<MarvelCharacter>>?>
        get() = _pagingData
    override val message: StateFlow<Action.Message?>
        get() = _message

    suspend fun setPagingData(pagingData: Flow<PagingData<MarvelCharacter>>) {
        _pagingData.emit(pagingData)
    }

    suspend fun setMessage(message: Action.Message) {
        _message.emit(message)
    }
}

//XML UI State
open class HomeUiState(
    val pagingData: State<Flow<PagingData<MarvelCharacter>>?>,
    val message: State<Action.Message?>
)

//Compose UI State
class HomeComposableUiState(
    pagingData: State<Flow<PagingData<MarvelCharacter>>?>,
    message: State<Action.Message?>,
    val snackBarHostState: SnackbarHostState
) : HomeUiState(pagingData, message)

@Composable
fun ComposeHomeUiState(uiState: UiState): HomeComposableUiState {
    val pagingDataState = uiState.pagingData.collectAsStateWithLifecycle()
    val messageState = uiState.message.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    return remember {
        HomeComposableUiState(pagingDataState, messageState, snackBarHostState)
    }
}