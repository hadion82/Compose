package com.example.domain

import androidx.paging.testing.asSnapshot
import com.example.data.repository.BookmarkRepository
import com.example.test.data.bookmark.FakeBookmarkRepository
import com.example.domain.mapper.DataToMarvelMapper
import com.example.domain.usecase.bookmark.AddBookmarkUseCase
import com.example.domain.usecase.bookmark.LoadBookmarkUseCase
import com.example.domain.usecase.bookmark.RemoveBookmarkUseCase
import com.example.testing.coroutine.MainCoroutineRule
import com.example.testing.coroutine.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ErrorCollector
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class BookmarkUseCaseTest {

    private val mainCoroutineRule = MainCoroutineRule()

    private val bookmarkRepository: BookmarkRepository =
        com.example.test.data.bookmark.FakeBookmarkRepository()

    @Test
    fun updateBookmark_CheckValueTest() = mainCoroutineRule.runTest{
        AddBookmarkUseCase(mainCoroutineRule.testDispatcher, bookmarkRepository)(
            AddBookmarkUseCase.Params(1)
        )

        val loadBookmarkUseCase = LoadBookmarkUseCase(
            bookmarkRepository, DataToMarvelMapper()
        )
        val addBookmarkResult = loadBookmarkUseCase(Unit).getOrNull()?.asSnapshot()?.first()
        assertNotNull(addBookmarkResult)
        assert(addBookmarkResult.mark)

        RemoveBookmarkUseCase(mainCoroutineRule.testDispatcher, bookmarkRepository)(
            RemoveBookmarkUseCase.Params(1)
        )
        val removeBookmarkResult = loadBookmarkUseCase(Unit).getOrNull()?.asSnapshot()?.first()
        assertNotNull(removeBookmarkResult)
        assertFalse(removeBookmarkResult.mark)
    }
}