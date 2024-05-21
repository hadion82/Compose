package com.example.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity(), MainActivityScope {

    override val intents: MutableSharedFlow<Intention> =
        MutableStateFlow(Intention.Effect.LoadCharacters)

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen(uiState = viewModel.state())
        }

        intents.onEach(viewModel::processIntent)
            .launchIn(lifecycleScope)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    with(MainActivityScope.default()) {
        MainScreen(uiState = MainUiState.idle())
    }
}