package com.example.data

import androidx.paging.testing.ErrorRecovery
import androidx.paging.testing.asSnapshot
import com.example.data.datasorce.CharacterRemoteTestingDataExceptionSource
import com.example.data.datasorce.CharacterRemoteTestingNullDataSource
import com.example.data.datasorce.CharacterRemoteTestingTotalNullDataSource
import com.example.data.repository.CharacterRepository
import com.example.testing.coroutine.runTest
import com.example.testing.data.FakeMarvelCharacterData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
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
internal class CharacterRepositoryUnitTest : RepositoryUnitTest() {
    @Test
    fun getPagingData_CheckValue() = mainCoroutineRule.runTest {
        safeTransaction {
            val pagedData = characterRepository.loadPagingData().asSnapshot()
            assertEquals(CharacterRepository.PAGE_SIZE * 2, pagedData.size)

        }
    }

    @Test
    fun getPagingData_CheckException() = mainCoroutineRule.runTest {
        safeTransaction {
            val characterExceptionRepository =
                createCharacterRepository(CharacterRemoteTestingDataExceptionSource())
            val pagedData = characterExceptionRepository.loadPagingData().asSnapshot(
                onError = { ErrorRecovery.RETURN_CURRENT_SNAPSHOT }
            )
            assertEquals(0, pagedData.size)
        }
    }

    @Test
    fun getPagingData_CheckNullData() = mainCoroutineRule.runTest {
        safeTransaction {
            val characterExceptionRepository =
                createCharacterRepository(CharacterRemoteTestingNullDataSource(remoteToResponseMapper))
            val pagedData = characterExceptionRepository.loadPagingData().asSnapshot(
                onError = { ErrorRecovery.RETURN_CURRENT_SNAPSHOT }
            )
            assertEquals(0, pagedData.size)
        }
    }

    @Test
    fun getPagingData_CheckTotalNullData() = mainCoroutineRule.runTest {
        safeTransaction {
            val characterExceptionRepository =
                createCharacterRepository(CharacterRemoteTestingTotalNullDataSource(remoteToResponseMapper))
            val pagedData = characterExceptionRepository.loadPagingData().asSnapshot(
                onError = { ErrorRecovery.RETURN_CURRENT_SNAPSHOT },
                loadOperations = {this.scrollTo(10)}
            )
            assertEquals(CharacterRepository.PAGE_SIZE, pagedData.size)
        }
    }

    @Test
    fun nullIdCharacterMapper_CheckValue() = mainCoroutineRule.runTest {
        val results = syncRepository.getCharacters(1, 5).results
        val resultsWithNull = results.toMutableList().apply {
            add(FakeMarvelCharacterData.createCharacterData(-1))
        }
        println("$resultsWithNull")
        assertEquals(INVALID_ID, resultsWithNull.map(characterEntityMapper).last().id)
        assertEquals(INVALID_ID, resultsWithNull.map(characterUpdatingEntityMapper).last().id)
    }

    @Test
    fun syncCharacter_CheckInsertValue() = mainCoroutineRule.runTest {
        safeTransaction {
            val results = syncRepository.getCharacters(1, 5).results
            assertNotNull(results)
            syncRepository.updateCharacters(results)
            val inserted = characterDao.getAll()
            val expected = results.map(characterEntityMapper)
            assertEquals(expected, inserted)

            val firstItem = characterRepository.getCharacterById(results[0].id)
            assertNotNull(firstItem)
            assertEquals(results[0].id, firstItem!!.id)

            val noItem = characterRepository.getCharacterById(INVALID_ID)
            assertNull(noItem)
        }
    }

    @Test
    fun updateCharacter_CheckUpdate() = mainCoroutineRule.runTest {
        safeTransaction {
            val limit = 5
            val firstOffset = 1
            val secondOffset = 3
            val firstResults = syncRepository.getCharacters(firstOffset, limit).results
            assertNotNull(firstResults)
            syncRepository.updateCharacters(firstResults)
            val secondResults = syncRepository.getCharacters(secondOffset, limit).results
            assertNotNull(secondResults)
            syncRepository.updateCharacters(secondResults)
            val inserted = characterDao.getAll()
            val offsetDiff = secondOffset - firstOffset
            assertEquals((limit * 2) - secondOffset, inserted.size)

            for (index in secondOffset..secondOffset + offsetDiff) {
                val expected = characterEntityMapper(secondResults[index - secondOffset])
                assertEquals(expected, inserted[index - 1])
            }
        }
    }
}