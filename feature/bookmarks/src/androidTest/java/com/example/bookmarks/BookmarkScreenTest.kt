package com.example.bookmarks

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.example.test.domain.bookmark.FakeLoadBookmarkUseCaseFactory
import com.example.test.domain.bookmark.FakeRemoveBookmarkUseCaseFactory
import com.example.test.domain.image.FakeSaveThumbnailUseCaseFactory
import com.example.testing.coroutine.MainCoroutineRule
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class BookmarkScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun bookmarkScreenTest() {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val bookmarkViewModel = BookmarkViewModel(
            FakeLoadBookmarkUseCaseFactory(),
            FakeRemoveBookmarkUseCaseFactory(mainCoroutineRule.testDispatcher),
            FakeSaveThumbnailUseCaseFactory(context, mainCoroutineRule.testDispatcher)
        )
        composeRule.setContent {
            BookmarkScreen(
                uiState = ComposeBookmarkUiState(uiState = bookmarkViewModel),
                presenter = ComposeBookmarkPresenter(
                viewModel = bookmarkViewModel,
                navController = rememberNavController()
                )
            )
        }
        composeRule.onNodeWithTag("bookmark_bar").assertExists()
    }
}