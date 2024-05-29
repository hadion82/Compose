package com.example.ui.common

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun SnackbarHostState.launch(
    scope: CoroutineScope,
    block: suspend SnackbarHostState.() -> Unit
) =
    scope.launch { block() }

@Composable
fun <T> SnackbarHostState.showSnackBarMessage(
    messageState: State<T?>,
    duration: SnackbarDuration = SnackbarDuration.Short,
    block: @Composable (T) -> String
) {
    val stateValue = messageState.value ?: return
    val scope = rememberCoroutineScope()
    val message = block(stateValue)

    launch(scope) {
        showSnackbar(message = message, duration = duration)
    }
}