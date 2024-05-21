package com.example.home

import androidx.compose.runtime.Composable

@Composable
fun MainRoute(uiState: MainComposableUiState, presenter: MainPresenter) {
    MainScreen(uiState, presenter)
}