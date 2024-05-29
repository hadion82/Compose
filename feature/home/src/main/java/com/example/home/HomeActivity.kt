package com.example.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import com.example.graph.BookmarkGraph
import com.example.graph.HomeGraph
import com.example.graph.ProfileGraph
import com.example.navigator.BookmarksNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    @Inject
    internal lateinit var homeGraph: HomeGraph

    @Inject
    internal lateinit var profileGraph: ProfileGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = HomeGraph.HOME_ROUTE
            ) {
                homeGraph.build(this, navController)
                profileGraph.build(this)
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    with(MainActivityScope.default()) {
//        MainScreen(uiState = MainUiState.idle())
//    }
//}