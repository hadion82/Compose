package com.example.domain

import android.content.Context
import androidx.paging.testing.asSnapshot
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.BookmarkRepository
import com.example.data.repository.ImageStreamRepository
import com.example.test.data.bookmark.FakeBookmarkRepository
import com.example.domain.mapper.DataToMarvelMapper
import com.example.domain.usecase.bookmark.AddBookmarkUseCase
import com.example.domain.usecase.bookmark.LoadBookmarkUseCase
import com.example.domain.usecase.bookmark.RemoveBookmarkUseCase
import com.example.domain.usecase.thumbnail.SaveThumbnailUseCase
import com.example.test.data.media.FakeImageRepository
import com.example.testing.coroutine.MainCoroutineRule
import com.example.testing.coroutine.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ErrorCollector
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class SaveThumbnailUseCaseTest {

    private val mainCoroutineRule = MainCoroutineRule()

    private val imageStreamRepository: ImageStreamRepository = FakeImageRepository()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun saveThumbnail_CheckValueTest() = mainCoroutineRule.runTest{
        val result = SaveThumbnailUseCase(context, mainCoroutineRule.testDispatcher, imageStreamRepository)(
            SaveThumbnailUseCase.Params("test")
        ).getOrNull()

        assertNotNull(result)
    }

    @Test
    fun saveNullUrl_CheckExceptionTest() = mainCoroutineRule.runTest{
        val result = SaveThumbnailUseCase(context, mainCoroutineRule.testDispatcher, imageStreamRepository)(
            SaveThumbnailUseCase.Params(null)
        ).getOrNull()

        assertNull(result)
    }
}