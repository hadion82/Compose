package com.example.bookmarks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.paging.PagingData
import com.example.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface BookmarkPresenter {
    fun onThumbnailClick(url: String?)
    fun onBookmarkClick(marvelCharacter: MarvelCharacter)
}