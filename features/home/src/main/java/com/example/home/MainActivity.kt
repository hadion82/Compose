package com.example.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.navigator.BookmarksNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), MainActivityScope {

    override val intents: MutableSharedFlow<Intention> =
        MutableStateFlow(Intention.Effect.LoadCharacters)

    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var bookmarksNavigator: BookmarksNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainRoute(
                uiState = ComposeMainUiState(viewModel.state()),
                presenter = ComposeMainPresenter(viewModel.intents, bookmarksNavigator)
            )
        }

        intents.onEach(viewModel::processIntent)
            .launchIn(lifecycleScope)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    with(MainActivityScope.default()) {
//        MainScreen(uiState = MainUiState.idle())
//    }
//}