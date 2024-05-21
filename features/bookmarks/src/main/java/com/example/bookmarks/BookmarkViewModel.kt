package com.example.bookmarks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.domain.usecase.bookmark.LoadBookmarkUseCase
import com.example.domain.usecase.bookmark.RemoveBookmarkUseCase
import com.example.domain.usecase.thumbnail.SaveThumbnailUseCase
import com.example.model.MarvelCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    loadBookmarkUseCase: LoadBookmarkUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase,
    private val saveThumbnailUseCase: SaveThumbnailUseCase,
) : ViewModel(), BookmarkPresenter {

    private val _pagingData: MutableStateFlow<Flow<PagingData<MarvelCharacter>>?> =
        MutableStateFlow(null)
    override val pagingData: State<Flow<PagingData<MarvelCharacter>>?>
        @Composable
        get() = _pagingData.collectAsStateWithLifecycle()

    private val _message: MutableStateFlow<Action.Message?> = MutableStateFlow(null)
    override val message: State<Action.Message?>
        @Composable
        get() = _message.collectAsStateWithLifecycle()

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

    override fun onBookmarkClick(marvelCharacter: MarvelCharacter) {
        viewModelScope.launch {
            removeBookmarkUseCase(RemoveBookmarkUseCase.Params(marvelCharacter.id))
                .onFailure { _message.emit(Action.Message.FailedToRemoveBookmark) }
        }
    }
}