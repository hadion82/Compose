package com.example.data

import androidx.paging.testing.ErrorRecovery
import androidx.paging.testing.asSnapshot
import com.example.testing.coroutine.runTest
import com.example.testing.data.FakeMarvelCharacterData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
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
internal class BookmarkRepositoryUnitTest: RepositoryUnitTest() {

    @Test
    fun updateBookmark_CheckValue() = mainCoroutineRule.runTest {
        safeTransaction {
            val results = syncRepository.getCharacters(1, 5).data?.results
            assertNotNull(results)
            syncRepository.syncCharacters(results!!)
            bookmarkRepository.addBookmark(results[0].id!!)
            bookmarkRepository.addBookmark(results[1].id!!)
            bookmarkRepository.addBookmark(INVALID_ID)
            val added = bookmarkRepository.loadPagingBookmarks().asSnapshot()
            assertEquals(2, added.size)

            bookmarkRepository.removeBookmark(results[0].id!!)
            bookmarkRepository.removeBookmark(results[1].id!!)
            bookmarkRepository.removeBookmark(INVALID_ID)
            val removed = bookmarkRepository.loadPagingBookmarks().asSnapshot()
            assertEquals(0, removed.size)
        }
    }
}