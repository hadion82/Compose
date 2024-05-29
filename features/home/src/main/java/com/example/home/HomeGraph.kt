package com.example.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.graph.HomeGraph
import com.example.navigator.BookmarksNavigator
import com.example.ui.theme.enterTransition
import com.example.ui.theme.exitTransition
import com.example.ui.theme.popEnterTransition
import com.example.ui.theme.popExitTransition
import javax.inject.Inject

internal class HomeGraphImpl @Inject constructor(
    private val navBookmarksNavigator: BookmarksNavigator
) : HomeGraph {

    override fun build(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) =
        with(navGraphBuilder) {
            composable(
                route = HomeGraph.HOME_ROUTE,
                popEnterTransition = popEnterTransition,
                popExitTransition = popExitTransition,
                enterTransition = enterTransition,
                exitTransition = exitTransition
            ) {
                HomeRoute(
                    navigator = navBookmarksNavigator,
                    navController = navHostController
                )
            }
        }
}