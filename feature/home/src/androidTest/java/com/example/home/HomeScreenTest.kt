package com.example.home

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import androidx.paging.testing.asSnapshot
import androidx.test.platform.app.InstrumentationRegistry
import com.example.home.tag.HomeTag
import com.example.navigator.BookmarksNavigator
import com.example.test.data.bookmark.FakeBookmarkRepository
import com.example.test.data.cached.CachedCharacterData
import com.example.test.data.character.FakeCharacterRepository
import com.example.test.domain.bookmark.FakeAddBookmarkUseCaseFactory
import com.example.test.domain.bookmark.FakeRemoveBookmarkUseCaseFactory
import com.example.test.domain.character.FakeLoadCharacterUseCaseFactory
import com.example.test.domain.image.FakeSaveThumbnailUseCaseFactory
import com.example.testing.coroutine.MainCoroutineRule
import com.example.ui.test.CommonTag
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val testDispatcher = mainCoroutineRule.testDispatcher
        val actionDispatcher = HomeActionDispatcherImpl()
        val cachedData = CachedCharacterData()
        val characterRepository = FakeCharacterRepository(cachedData)
        val bookmarkRepository = FakeBookmarkRepository(cachedData)
        homeViewModel = HomeViewModel(
            testDispatcher,
            HomeViewModelDelegateImpl(),
            HomeIntentProcessor(
                HomeEventHandler(
                    actionDispatcher,
                    FakeAddBookmarkUseCaseFactory(testDispatcher, bookmarkRepository),
                    FakeRemoveBookmarkUseCaseFactory(testDispatcher, bookmarkRepository)
                ),
                HomeEffectHandler(
                    actionDispatcher,
                    FakeLoadCharacterUseCaseFactory(characterRepository),
                    FakeSaveThumbnailUseCaseFactory(context, testDispatcher)
                )
            ),
            actionDispatcher,
            HomeActionReducerImpl()
        )
    }

    @Test
    fun checkHomeScreenContent() = runTest {
        val uiState = homeViewModel.state()
        val bookmarkNavigation = createBookMarkNavigator()
        composeRule.setContent {
            HomeScreen(
                uiState = ComposeHomeUiState(uiState = uiState),
                presenter = ComposeHomePresenter(
                    intents = homeViewModel.intents,
                    bookmarksNavigator = bookmarkNavigation,
                    navController = rememberNavController()
                ),
                enableLoading = false
            )
        }

        val testId = homeViewModel.state().pagingData.value?.asSnapshot()?.get(0)?.id
        composeRule.onNodeWithTag(HomeTag.Bar.OPEN_BOOKMARK).assertExists()
        composeRule.onNodeWithTag(HomeTag.Content.CHARACTER_LAZY_COLUMN).assertExists()
        composeRule.onNodeWithTag(CommonTag.Character.THUMBNAIL + testId)
            .assertExists()
            .assertHasClickAction()
        composeRule.onNodeWithTag(CommonTag.Character.INFORMATION + testId)
            .assertExists()
            .assertHasClickAction()
    }

    private fun createBookMarkNavigator() : BookmarksNavigator =
        object : BookmarksNavigator {
            override fun navigate(
                activity: Activity,
                intentBuilder: Intent.() -> Intent,
                withFinish: Boolean
            ) {}
        }
}