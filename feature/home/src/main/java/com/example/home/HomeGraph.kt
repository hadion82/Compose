package com.example.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.graph.HomeGraph
import com.example.navigator.BookmarksNavigator
import com.example.design.animation.enterTransition
import com.example.design.animation.exitTransition
import com.example.design.animation.popEnterTransition
import com.example.design.animation.popExitTransition
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
                popEnterTransition = com.example.design.animation.popEnterTransition,
                popExitTransition = com.example.design.animation.popExitTransition,
                enterTransition = com.example.design.animation.enterTransition,
                exitTransition = com.example.design.animation.exitTransition
            ) {
                HomeRoute(
                    navigator = navBookmarksNavigator,
                    navController = navHostController
                )
            }
        }
}