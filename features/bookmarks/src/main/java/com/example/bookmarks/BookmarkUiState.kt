package com.example.bookmarks

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import com.example.model.MarvelCharacter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.shareIn

internal interface UiState {
    val pagingData: StateFlow<Flow<PagingData<MarvelCharacter>>?>
    val message: StateFlow<Action.Message?>
}

//XML UI State
internal open class BookmarkUiState(
    val pagingData: State<Flow<PagingData<MarvelCharacter>>?>,
    val message: State<Action.Message?>
)

//Compose UI State
internal class BookmarkComposableUiState(
    pagingData: State<Flow<PagingData<MarvelCharacter>>?>,
    message: State<Action.Message?>,
    val snackBarHostState: SnackbarHostState
) : BookmarkUiState(pagingData, message)

@Composable
internal fun ComposeBookmarkUiState(uiState: UiState): BookmarkComposableUiState {
    val pagingDataState = uiState.pagingData.collectAsStateWithLifecycle()
    val messageState = uiState.message.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    return remember {
        BookmarkComposableUiState(
            pagingDataState, messageState, snackBarHostState
        )
    }
}