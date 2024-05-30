package com.example.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.graph.HomeGraph
import com.example.graph.ProfileGraph
import dagger.hilt.android.AndroidEntryPoint
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