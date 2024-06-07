package com.example.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.datasorce.CharacterRemoteTestingDataSource
import com.example.data.datasource.local.CharacterLocalDataSource
import com.example.data.datasource.local.CharacterLocalDataSourceImpl
import com.example.data.datasource.remote.CharacterRemoteDataSource
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
import com.example.database.LocalDataBase
import com.example.database.dao.CharacterDao
import com.example.datastore.preferences.PreferencesDatastore
import com.example.testing.coroutine.MainCoroutineRule
import com.example.testing.datastore.TestingPreferencesDatastore
import org.junit.Before
import org.junit.Rule
import org.junit.rules.ErrorCollector

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

internal abstract class RepositoryUnitTest {

    companion object {
        const val INVALID_ID = -1
    }

    @get:Rule
    val collectorRule = ErrorCollector()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var database: LocalDataBase

    protected lateinit var bookmarkRepository: BookmarkRepository

    protected lateinit var syncRepository: SyncRepository

    protected lateinit var characterRepository: CharacterRepository

    protected lateinit var characterDao: CharacterDao

    private lateinit var characterLocalDataSource: CharacterLocalDataSource

    private lateinit var characterRemoteDataSource: CharacterRemoteDataSource

    private lateinit var pagingPreference: PreferencesDatastore

    protected val characterEntityMapper = CharacterEntityMapper()

    protected val characterUpdatingEntityMapper = CharacterUpdatingEntityMapper()

    private val characterDataMapper = CharacterDataMapper()


    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(
            context, LocalDataBase::class.java
        ).build()
        characterDao = database.characterDao()
        characterLocalDataSource = CharacterLocalDataSourceImpl(characterDao)
        characterRemoteDataSource = CharacterRemoteTestingDataSource()

        pagingPreference = TestingPreferencesDatastore(context)

        bookmarkRepository = BookmarkRepositoryImpl(
            characterLocalDataSource,
            characterDataMapper
        )

        syncRepository = SyncRepositoryImpl(
            localDataSource = characterLocalDataSource,
            remoteDataSource = characterRemoteDataSource,
            characterEntityMapper = characterEntityMapper,
            updatingEntityMapper = characterUpdatingEntityMapper
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

    protected suspend fun createCharacterRepository(characterDataSource: CharacterRemoteDataSource): CharacterRepository {
        val syncExceptionRepository = SyncRepositoryImpl(
            localDataSource = characterLocalDataSource,
            remoteDataSource = characterDataSource,
            characterEntityMapper = characterEntityMapper,
            updatingEntityMapper = characterUpdatingEntityMapper
        )
        return CharacterRepositoryImpl(
            characterLocalDataSource,
            characterRemoteDataSource,
            CharacterMediator(
                syncRepository = syncExceptionRepository,
                dataStore = pagingPreference
            ),
            characterDataMapper
        )
    }

    protected suspend fun safeTransaction(block: suspend () -> Unit) {
        block()
        characterDao.deleteAll()
        database.close()
    }
}