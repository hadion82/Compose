package com.example.data

import androidx.paging.testing.asSnapshot
import com.example.testing.coroutine.runTest
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
internal class BookmarkRepositoryUnitTest : RepositoryUnitTest() {

    @Test
    fun updateBookmark_clickBookmark_CheckValue() = mainCoroutineRule.runTest {
        safeTransaction {
            val results = syncRepository.getCharacters(1, 5).results
            assertNotNull(results)
            syncRepository.updateCharacters(results)
            with(bookmarkRepository) {
                addBookmark(results[0].id)
                addBookmark(results[1].id)
                addBookmark(INVALID_ID)
                val added = loadPagingBookmarks().asSnapshot()
                assertEquals(2, added.size)

                removeBookmark(results[0].id)
                removeBookmark(results[1].id)
                removeBookmark(INVALID_ID)
                val removed = loadPagingBookmarks().asSnapshot()
                assertEquals(0, removed.size)
            }
        }
    }
}