package com.example.bookmarks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.paging.PagingData
import com.example.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface BookmarkPresenter {

    companion object {
        fun default() = object : BookmarkPresenter {
            override val pagingData: State<Flow<PagingData<MarvelCharacter>>?>
                @Composable
                get() = remember { mutableStateOf(null) }
            override val message: State<Action.Message?>
                @Composable
                get() = remember { mutableStateOf(null) }

            override fun onThumbnailClick(url: String?) {}
            override fun onBookmarkClick(marvelCharacter: MarvelCharacter) {}
        }
    }

    @get:Composable
    val pagingData: State<Flow<PagingData<MarvelCharacter>>?>

    @get:Composable
    val message: State<Action.Message?>

    fun onThumbnailClick(url: String?)
    fun onBookmarkClick(marvelCharacter: MarvelCharacter)
}