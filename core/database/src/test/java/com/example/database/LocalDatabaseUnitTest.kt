package com.example.database

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.database.dao.CharacterDao
import com.example.database.test.MemoryLocalDatabase
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(RobolectricTestRunner::class)
class LocalDatabaseUnitTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var bookmarkDao: CharacterDao
    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val database = MemoryLocalDatabase.create(context)
        bookmarkDao = database.characterDao()
    }

    @Test
    fun addBookmark_CheckInsertValue() = TestScope(dispatcher).runTest {
        val entityCount = 5
        val entities = FakeCharacterEntityData.createEntities(entityCount)
        bookmarkDao.insertOrReplace(entities)
        assertEquals(bookmarkDao.getAll().size, entityCount)
        bookmarkDao.delete(entities)
    }

    @Test
    fun updateBookmark_CheckUpdate() = TestScope(dispatcher).runTest {
        val entity = FakeCharacterEntityData.oneEntity()
        bookmarkDao.insertOrReplace(entity)

        bookmarkDao.updateBookmark(entity.id, true)
        val marked = bookmarkDao.getBookMarksById(entity.id)
        assertEquals(marked?.mark, true)

        bookmarkDao.updateBookmark(entity.id, false)
        val unmarked = bookmarkDao.getBookMarksById(entity.id)
        assertEquals(unmarked?.mark, false)

        bookmarkDao.delete(entity)
    }
}