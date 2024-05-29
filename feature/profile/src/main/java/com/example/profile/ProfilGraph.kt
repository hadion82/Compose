package com.example.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.graph.ProfileGraph
import com.example.ui.theme.enterTransition
import com.example.ui.theme.exitTransition
import com.example.ui.theme.popEnterTransition
import com.example.ui.theme.popExitTransition
import timber.log.Timber
import javax.inject.Inject

internal class ProfileGraphImpl @Inject constructor() : ProfileGraph {

    override fun build(
        navGraphBuilder: NavGraphBuilder
    ) = with(navGraphBuilder) {
        composable(
            route = ProfileGraph.Route.PROFILE,
            arguments = listOf(navArgument(ProfileGraph.Argument.PROFILE_ID) {
                type = NavType.IntType
            }),
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
            enterTransition = enterTransition,
            exitTransition = exitTransition
        ) { entry ->
            val id = entry.arguments?.getInt(ProfileGraph.Argument.PROFILE_ID) ?: return@composable
            ProfileRoute(id = id)
        }
    }
}