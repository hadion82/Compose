package com.example.home

import android.content.Context
import androidx.paging.testing.asSnapshot
import androidx.test.core.app.ApplicationProvider
import com.example.test.data.bookmark.FakeBookmarkRepository
import com.example.test.data.cached.CachedCharacterData
import com.example.test.data.character.FakeCharacterRepository
import com.example.test.data.constant.TestConstant.TEST_ID
import com.example.test.domain.bookmark.FakeAddBookmarkUseCaseFactory
import com.example.test.domain.bookmark.FakeRemoveBookmarkUseCaseFactory
import com.example.test.domain.character.FakeLoadCharacterUseCaseFactory
import com.example.test.domain.image.FakeSaveThumbnailUseCaseFactory
import com.example.testing.coroutine.MainCoroutineRule
import com.example.testing.coroutine.runTest
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class HomeViewModelTest {

    private val mainCoroutineRule = MainCoroutineRule()

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
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
    fun loadCharacter_checkValue() = runTest(mainCoroutineRule.testDispatcher){
        val item = homeViewModel.state().pagingData.value?.asSnapshot()?.get(0)
        assertEquals(TEST_ID, item?.id)
    }

    @Test
    fun updateBookmark_WhenBookmarkClick_checkMarked() = runTest(mainCoroutineRule.testDispatcher){
        homeViewModel.processIntent(Intention.Event.AddBookmark(TEST_ID))
        var item = homeViewModel.state().pagingData.value?.asSnapshot()?.get(0)
        assertEquals(true, item?.mark)

        homeViewModel.processIntent(Intention.Event.RemoveBookmark(TEST_ID))
        item = homeViewModel.state().pagingData.value?.asSnapshot()?.get(0)
        assertEquals(false, item?.mark)
    }

    @Test
    fun saveImage_WhenImageClick_CheckResultMessage() = mainCoroutineRule.runTest {
        homeViewModel.processIntent(Intention.Effect.SaveThumbnail("test"))
        val message = homeViewModel.state().message.value
        assertEquals(Action.Message.SuccessToSaveImage, message)
    }
}