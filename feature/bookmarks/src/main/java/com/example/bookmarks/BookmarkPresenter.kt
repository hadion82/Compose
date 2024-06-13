package com.example.bookmarks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.graph.ProfileGraph

internal interface IntentPresenter {
    fun onThumbnailClick(url: String?)
    fun onBookmarkClick(id: Int, marked: Boolean)
}
internal interface BookmarkPresenter: IntentPresenter {
    fun onDescriptionClick(id: Int)
}

internal class BookmarkPresenterImpl (
    intentPresenter: IntentPresenter,
    private val navController: NavController
): BookmarkPresenter, IntentPresenter by intentPresenter {
    override fun onDescriptionClick(id: Int) {
        val route = ProfileGraph.Route.makeRoute(id)
        navController.navigate(route, navOptions = null)
    }

}

@Composable
internal fun ComposeBookmarkPresenter(
    viewModel: BookmarkViewModel,
    navController: NavController
): BookmarkPresenter {
    return remember {
        BookmarkPresenterImpl(viewModel, navController)
    }
}