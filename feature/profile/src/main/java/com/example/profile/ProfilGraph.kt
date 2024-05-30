package com.example.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.graph.ProfileGraph
import com.example.design.animation.enterTransition
import com.example.design.animation.exitTransition
import com.example.design.animation.popEnterTransition
import com.example.design.animation.popExitTransition
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
            popEnterTransition = com.example.design.animation.popEnterTransition,
            popExitTransition = com.example.design.animation.popExitTransition,
            enterTransition = com.example.design.animation.enterTransition,
            exitTransition = com.example.design.animation.exitTransition
        ) { entry ->
            val id = entry.arguments?.getInt(ProfileGraph.Argument.PROFILE_ID) ?: return@composable
            ProfileRoute(id = id)
        }
    }
}