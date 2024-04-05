package com.example.compose.ui.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.paging.PagingData
import com.example.data.model.CharacterItem
import kotlinx.coroutines.flow.Flow

interface BookmarkPresenter {

    companion object {
        fun default() = object : BookmarkPresenter {
            override val pagingData: State<Flow<PagingData<CharacterItem>>?>
                @Composable
                get() = remember { mutableStateOf(null) }
            override val message: State<Action.Message?>
                @Composable
                get() = remember { mutableStateOf(null) }

            override fun onThumbnailClick(url: String?) {}
            override fun onBookmarkClick(item: CharacterItem) {}
        }
    }

    @get:Composable
    val pagingData: State<Flow<PagingData<CharacterItem>>?>

    @get:Composable
    val message: State<Action.Message?>

    fun onThumbnailClick(url: String?)
    fun onBookmarkClick(item: CharacterItem)
}