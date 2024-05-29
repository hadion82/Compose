package com.example.bookmarks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.graph.BookmarkGraph
import com.example.graph.ProfileGraph
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookmarkActivity : ComponentActivity() {

    @Inject
    internal lateinit var bookmarkGraph: BookmarkGraph

    @Inject
    internal lateinit var profileGraph: ProfileGraph

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = BookmarkGraph.BOOK_MARK_ROUTE
            ) {
                bookmarkGraph.build(this, navController)
                profileGraph.build(this)
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    BookmarkScreen(presenter = BookmarkPresenter.default())
//}