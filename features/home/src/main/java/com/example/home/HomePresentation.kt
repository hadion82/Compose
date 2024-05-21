package com.example.home

import androidx.paging.PagingData
import com.example.model.MarvelCharacter
import com.example.shared.reducer.ActionReducer
import kotlinx.coroutines.flow.Flow
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
        override suspend fun reduce(state: MutableHomeUiState) =
            state.setPagingData(data)
    }

    sealed interface Message : Action {
        data object FailedToBookmark : Message
        data object FailedToDeleteBookmark : Message
        data object SuccessToSaveImage : Message
        data object FailedToSaveImage : Message
        data object FailedToLoadData : Message
        data class ShowMessage(val message: String) : Message

        override suspend fun reduce(state: MutableHomeUiState) =
            state.setMessage(this)
    }

    suspend fun reduce(state: MutableHomeUiState)
}

interface HomeActionReducer : ActionReducer<UiState, Action>

class HomeActionReducerImpl @Inject constructor() : HomeActionReducer {

    private val state: MutableHomeUiState = MutableHomeUiState.idle()

    override suspend fun reduce(action: Action) {
        Timber.d("action -> $action")
        action.reduce(state)
    }

    override fun state(): MutableHomeUiState = state
}