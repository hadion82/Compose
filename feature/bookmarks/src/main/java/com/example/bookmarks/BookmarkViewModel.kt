package com.example.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.domain.usecase.bookmark.LoadBookmarkUseCase
import com.example.domain.usecase.bookmark.RemoveBookmarkUseCase
import com.example.domain.usecase.thumbnail.SaveThumbnailUseCase
import com.example.model.MarvelCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    loadBookmarkUseCase: LoadBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    private val saveThumbnailUseCase: SaveThumbnailUseCase,
) : ViewModel(), UiState, IntentPresenter {

    private val _pagingData: MutableStateFlow<Flow<PagingData<MarvelCharacter>>?> =
        MutableStateFlow(null)
    override val pagingData: StateFlow<Flow<PagingData<MarvelCharacter>>?>
        get() = _pagingData

    private val _message: MutableStateFlow<Action.Message?> = MutableStateFlow(null)
    override val message: StateFlow<Action.Message?>
        get() = _message

    init {
        viewModelScope.launch {
            loadBookmarkUseCase(Unit)
                .onSuccess { _pagingData.emit(it) }
                .onFailure { _message.emit(Action.Message.FailedToLoadData) }
        }
    }

    override fun onThumbnailClick(url: String?) {
        viewModelScope.launch {
            saveThumbnailUseCase(SaveThumbnailUseCase.Params(url))
                .onSuccess { _message.emit(Action.Message.SuccessToSaveImage) }
                .onFailure { _message.emit(Action.Message.FailedToSaveImage) }
        }
    }

    override fun onBookmarkClick(id: Int, marked: Boolean) {
        viewModelScope.launch {
            removeBookmarkUseCase(RemoveBookmarkUseCase.Params(id))
                .onFailure { _message.emit(Action.Message.FailedToRemoveBookmark) }
        }
    }
}