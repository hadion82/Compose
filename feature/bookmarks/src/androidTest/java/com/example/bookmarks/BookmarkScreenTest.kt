package com.example.bookmarks

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import androidx.paging.testing.asSnapshot
import androidx.test.platform.app.InstrumentationRegistry
import com.example.test.data.bookmark.FakeBookmarkRepository
import com.example.test.data.cached.CachedCharacterData
import com.example.test.domain.bookmark.FakeLoadBookmarkUseCaseFactory
import com.example.test.domain.bookmark.FakeRemoveBookmarkUseCaseFactory
import com.example.test.domain.image.FakeSaveThumbnailUseCaseFactory
import com.example.testing.coroutine.MainCoroutineRule
import com.example.testing.coroutine.runTest
import com.example.ui.test.CommonTag
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
    fun checkBookmarkScreenContent() = mainCoroutineRule.runTest {
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val bookmarkRepository = FakeBookmarkRepository(CachedCharacterData())
        val bookmarkViewModel = BookmarkViewModel(
            FakeLoadBookmarkUseCaseFactory(),
            FakeRemoveBookmarkUseCaseFactory(mainCoroutineRule.testDispatcher, bookmarkRepository),
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
        val testId = bookmarkViewModel.pagingData.value?.asSnapshot()?.get(0)?.id
        composeRule.onNodeWithTag(BookmarksTag.Bar.BOOKMARK_BAR).assertExists()
        composeRule.onNodeWithTag(BookmarksTag.Content.BOOKMARK_COLUMN).assertExists()
        composeRule.onNodeWithTag(CommonTag.Character.THUMBNAIL + testId)
            .assertExists()
            .assertHasClickAction()
        composeRule.onNodeWithTag(CommonTag.Character.INFORMATION + testId)
            .assertExists()
            .assertHasClickAction()
    }
}