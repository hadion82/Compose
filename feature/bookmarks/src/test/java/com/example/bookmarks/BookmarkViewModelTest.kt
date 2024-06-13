package com.example.bookmarks

import android.content.Context
import androidx.paging.testing.asSnapshot
import androidx.test.core.app.ApplicationProvider
import com.example.test.domain.bookmark.FakeLoadBookmarkUseCaseFactory
import com.example.test.domain.bookmark.FakeRemoveBookmarkUseCaseFactory
import com.example.test.data.constant.TestConstant.TEST_ID
import com.example.test.domain.image.FakeSaveThumbnailUseCaseFactory
import com.example.testing.coroutine.MainCoroutineRule
import com.example.testing.coroutine.runTest
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
class BookmarkViewModelTest {

    private val mainCoroutineRule = MainCoroutineRule()

    private lateinit var bookmarkViewModel: BookmarkViewModel

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        bookmarkViewModel = BookmarkViewModel(
            FakeLoadBookmarkUseCaseFactory(),
            FakeRemoveBookmarkUseCaseFactory(mainCoroutineRule.testDispatcher),
            FakeSaveThumbnailUseCaseFactory(context, mainCoroutineRule.testDispatcher)
        )
    }
    @Test
    fun removeBookmark_CheckState() = mainCoroutineRule.runTest {
        bookmarkViewModel.onBookmarkClick(TEST_ID, true)
        val bookmarks = bookmarkViewModel.pagingData.value?.asSnapshot()
        val unmarked = bookmarks?.find { it.id == TEST_ID }
        assertEquals(false, unmarked?.mark)
    }

    @Test
    fun saveImage_CheckValue() = mainCoroutineRule.runTest {
        bookmarkViewModel.onThumbnailClick("test")
        val message = bookmarkViewModel.message.value
        assertEquals(Action.Message.SuccessToSaveImage, message)
    }
}