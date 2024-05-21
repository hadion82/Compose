package com.example.bookmarks

import androidx.compose.runtime.Composable

@Composable
fun BookmarkRoute(uiState: BookmarkComposableUiState, presenter: BookmarkPresenter) {
    BookmarkScreen(uiState, presenter)
}