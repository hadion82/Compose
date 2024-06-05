package com.example.data

import android.content.Context
import androidx.paging.testing.asSnapshot
import androidx.test.core.app.ApplicationProvider
import com.example.data.datasource.local.CharacterLocalDataSource
import com.example.data.datasource.local.CharacterLocalDataSourceImpl
import com.example.data.mapper.CharacterDataMapper
import com.example.data.mapper.CharacterEntityMapper
import com.example.data.mapper.CharacterUpdatingEntityMapper
import com.example.data.mediator.CharacterMediator
import com.example.data.repository.BookmarkRepository
import com.example.data.repository.BookmarkRepositoryImpl
import com.example.data.repository.CharacterRepository
import com.example.data.repository.CharacterRepositoryImpl
import com.example.data.repository.SyncRepository
import com.example.data.repository.SyncRepositoryImpl
import com.example.database.dao.CharacterDao
import com.example.datastore.preferences.PagingPreferencesDatastore
import com.example.testing.coroutine.MainCoroutineRule
import com.example.testing.coroutine.runTest
import com.example.testing.database.FakeLocalDatabase
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ErrorCollector
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
class CharacterSyncUnitTest {

    @get:Rule
    val collectorRule = ErrorCollector()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var database: FakeLocalDatabase

    private lateinit var bookmarkRepository: BookmarkRepository

    private lateinit var characterRepository: CharacterRepository

    private lateinit var syncRepository: SyncRepository

    private lateinit var characterDao: CharacterDao

    private val characterEntityMapper = CharacterEntityMapper()


    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        database = FakeLocalDatabase.create(context)
        characterDao = database.characterDao()
        val characterLocalDataSource = CharacterLocalDataSourceImpl(characterDao)
        val characterRemoteDataSource = TestCharacterRemoteDataSource()
        val pagingPreference = PagingPreferencesDatastore(context)
        val characterDataMapper = CharacterDataMapper()

        bookmarkRepository = BookmarkRepositoryImpl(
            characterLocalDataSource,
            characterDataMapper
        )

        syncRepository = SyncRepositoryImpl(
            localDataSource = characterLocalDataSource,
            remoteDataSource = characterRemoteDataSource,
            characterEntityMapper = CharacterEntityMapper(),
            updatingEntityMapper = CharacterUpdatingEntityMapper()
        )

        characterRepository = CharacterRepositoryImpl(
            characterLocalDataSource,
            characterRemoteDataSource,
            CharacterMediator(
                syncRepository = syncRepository,
                dataStore = pagingPreference
            ),
            characterDataMapper
        )
    }

    @Test
    fun addCharacter_CheckInsertValue() = mainCoroutineRule.runTest {
        val results = syncRepository.getCharacters(1, 5).data?.results
        assertNotNull(results)
        syncRepository.syncCharacters(results!!)
        val inserted = characterDao.getAll()
        assertEquals(results.map(characterEntityMapper), inserted)
        database.clearAllTables()
    }

    @Test
    fun updateCharacter_CheckUpdate() = mainCoroutineRule.runTest {
        val limit = 5
        val firstOffset = 1
        val secondOffset = 3
        val firstResults = syncRepository.getCharacters(firstOffset, limit).data?.results
        assertNotNull(firstResults)
        syncRepository.syncCharacters(firstResults!!)
        val secondResults = syncRepository.getCharacters(secondOffset, limit).data?.results
        assertNotNull(secondResults)
        syncRepository.syncCharacters(secondResults!!)
        val inserted = characterDao.getAll()
        val offsetDiff = secondOffset - firstOffset
        assertEquals((limit * 2) - secondOffset, inserted.size)


        for(index in secondOffset  .. secondOffset + offsetDiff) {
            assertEquals(characterEntityMapper(secondResults[index - secondOffset]), inserted[index - 1])
        }
        database.clearAllTables()
    }
}