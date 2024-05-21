package com.example.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.navigator.BookmarksNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : ComponentActivity(), HomeActivityScope {

    override val intents: MutableSharedFlow<Intention> =
        MutableStateFlow(Intention.Effect.LoadCharacters)

    private val viewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var bookmarksNavigator: BookmarksNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainRoute(
                uiState = ComposeHomeUiState(viewModel.state()),
                presenter = ComposeHomePresenter(viewModel.intents, bookmarksNavigator)
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