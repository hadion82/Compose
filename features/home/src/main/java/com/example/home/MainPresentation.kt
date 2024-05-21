package com.example.home

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import com.example.model.MarvelCharacter
import com.example.shared.reducer.ActionReducer
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

sealed interface Intention {
    sealed interface Effect : Intention {
        data object LoadCharacters : Effect

        data class SaveThumbnail(val path: String?) : Effect
    }

    sealed interface Event : Intention {
        data class AddBookmark(val id: Int) : Event
        data class RemoveBookmark(val id: Int) : Event
    }
}

sealed interface Action {

    data class LoadPagingData(
        val data: Flow<PagingData<MarvelCharacter>>
    ) : Action {
        override suspend fun reduce(state: MainUiState) =
            state.setPagingData(data)
    }

    sealed interface Message : Action {
        data object FailedToBookmark : Message
        data object FailedToDeleteBookmark : Message
        data object SuccessToSaveImage : Message
        data object FailedToSaveImage : Message
        data object FailedToLoadData : Message

        override suspend fun reduce(state: MainUiState) =
            state.setMessage(this)
    }

    suspend fun reduce(state: MainUiState)
}

interface MainActionReducer : ActionReducer<UiState, Action>

class MainActionReducerImpl @Inject constructor() : MainActionReducer {

    private val state: MainUiState = MainUiState.idle()

    override suspend fun reduce(action: Action) {
        Timber.d("action -> $action")
        action.reduce(state)
    }

    override fun state(): MainUiState = state
}

interface UiState {
    @get:Composable
    val pagingData: State<Flow<PagingData<MarvelCharacter>>?>
    @get:Composable
    val message: State<Action.Message?>
}

class MainUiState(
    private val _pagingData: MutableStateFlow<Flow<PagingData<MarvelCharacter>>?>,
    private val _message: MutableStateFlow<Action.Message?>
) : UiState {
    companion object {
        fun idle() = MainUiState(MutableStateFlow(null), MutableStateFlow(null))
    }

    override val pagingData: State<Flow<PagingData<MarvelCharacter>>?>
        @Composable
        get() = _pagingData.collectAsStateWithLifecycle()
    override val message: State<Action.Message?>
        @Composable
        get() = _message.collectAsStateWithLifecycle()

    suspend fun setPagingData(pagingData: Flow<PagingData<MarvelCharacter>>) {
        _pagingData.emit(pagingData)
    }

    suspend fun setMessage(message: Action.Message) {
        _message.emit(message)
    }
}

class PermissionState @OptIn(ExperimentalPermissionsApi::class) constructor(
    private val dispatcher: MainScopedDispatcher,
    private val permissionState: PermissionState
) {
    @OptIn(ExperimentalPermissionsApi::class)
    fun onThumbnailClick(url: String?) = run {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ||
            permissionState.status.isGranted
        ) {
            dispatcher.dispatch(Intention.Effect.SaveThumbnail(url))
        } else {
            permissionState.launchPermissionRequest()
        }
    }

    fun onBookmarkClick(item: MarvelCharacter) = run {
        dispatcher.dispatch(
            if (item.mark) Intention.Event.AddBookmark(item.id)
            else Intention.Event.RemoveBookmark(item.id)
        )
    }
}