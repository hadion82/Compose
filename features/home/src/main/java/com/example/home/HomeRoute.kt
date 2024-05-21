package com.example.home

import androidx.compose.runtime.Composable

@Composable
fun MainRoute(uiState: HomeComposableUiState, presenter: MainPresenter) {
    MainScreen(uiState, presenter)
}