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
        data class SnackBarMessage(val message: String) : Event
    }
}

sealed interface Action {

    data class LoadPagingData(
        val data: Flow<PagingData<MarvelCharacter>>
    ) : Action {
        override suspend fun reduce(state: MutableMainUiState) =
            state.setPagingData(data)
    }

    sealed interface Message : Action {
        data object FailedToBookmark : Message
        data object FailedToDeleteBookmark : Message
        data object SuccessToSaveImage : Message
        data object FailedToSaveImage : Message
        data object FailedToLoadData : Message
        data class ShowMessage(val message: String) : Message

        override suspend fun reduce(state: MutableMainUiState) =
            state.setMessage(this)
    }

    sealed interface Navigation : Action {
        class OpenBookmark : Navigation {
            override suspend fun reduce(state: MutableMainUiState) {
                state.setNavigation(this)
            }
        }
    }

    suspend fun reduce(state: MutableMainUiState)
}

interface MainActionReducer : ActionReducer<UiState, Action>

class MainActionReducerImpl @Inject constructor() : MainActionReducer {

    private val state: MutableMainUiState = MutableMainUiState.idle()

    override suspend fun reduce(action: Action) {
        Timber.d("action -> $action")
        action.reduce(state)
    }

    override fun state(): MutableMainUiState = state
}