package com.example.domain

import com.example.test.data.constant.TestConstant.TEST_ID
import com.example.test.data.sync.FakeSyncRepository
import com.example.domain.usecase.character.SyncCharacterUseCase
import com.example.testing.coroutine.MainCoroutineRule
import com.example.testing.coroutine.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ErrorCollector
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class SyncCharacterUseCaseTest {

    private val mainCoroutineRule = MainCoroutineRule()

    private val syncRepository = FakeSyncRepository()

    @Test
    fun loadCharacter_CheckValueTest() = mainCoroutineRule.runTest {
        SyncCharacterUseCase(mainCoroutineRule.testDispatcher, syncRepository)(
            SyncCharacterUseCase.Params(1)
        )

        val results = syncRepository.getResults()

        assertEquals(1, results.size)
        assertEquals(TEST_ID, results.first().id)
    }
}