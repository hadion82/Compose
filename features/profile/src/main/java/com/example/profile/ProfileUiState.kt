package com.example.profile

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.MarvelCharacter
import kotlinx.coroutines.flow.StateFlow

internal interface UiState {
    val profileData: StateFlow<MarvelCharacter?>
    val message: StateFlow<Action.Message?>
}

//XML UI State
internal open class ProfileUiState(
    val characterData: State<MarvelCharacter?>,
    val message: State<Action.Message?>
)

//Compose UI State
internal class ProfileComposableUiState(
    characterData: State<MarvelCharacter?>,
    message: State<Action.Message?>,
    val snackBarHostState: SnackbarHostState
) : ProfileUiState(characterData, message)

@Composable
internal fun ComposeProfileUiState(uiState: UiState): ProfileComposableUiState {
    val characterDataState = uiState.profileData.collectAsStateWithLifecycle()
    val messageState = uiState.message.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    return remember {
        ProfileComposableUiState(
            characterDataState, messageState, snackBarHostState
        )
    }
}