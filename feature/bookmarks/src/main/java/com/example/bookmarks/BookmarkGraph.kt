package com.example.bookmarks

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.graph.BookmarkGraph
import com.example.ui.theme.enterTransition
import com.example.ui.theme.exitTransition
import com.example.ui.theme.popEnterTransition
import com.example.ui.theme.popExitTransition
import javax.inject.Inject

class BookmarkGraphImpl @Inject constructor() : BookmarkGraph {

    override fun build(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) =
        with(navGraphBuilder) {
            composable(
                route = BookmarkGraph.BOOK_MARK_ROUTE,
                popEnterTransition = popEnterTransition,
                popExitTransition = popExitTransition,
                enterTransition = enterTransition,
                exitTransition = exitTransition
            ) {
                BookmarkRoute(navHostController = navHostController)
            }
        }
}