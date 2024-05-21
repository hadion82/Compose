package com.example.bookmarks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarkActivity : ComponentActivity() {

    private val viewModel by viewModels<BookmarkViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BookmarkRoute(
                uiState = ComposeBookmarkUiState(viewModel),
                presenter = viewModel
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    BookmarkScreen(presenter = BookmarkPresenter.default())
//}