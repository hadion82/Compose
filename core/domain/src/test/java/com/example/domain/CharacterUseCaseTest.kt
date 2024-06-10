package com.example.domain

import androidx.paging.testing.asSnapshot
import com.example.data.repository.CharacterRepository
import com.example.domain.data.character.FakeCharacterRepository
import com.example.domain.mapper.DataToMarvelMapper
import com.example.domain.usecase.character.LoadCharacterUseCase
import com.example.testing.coroutine.MainCoroutineRule
import com.example.testing.coroutine.runTest
import org.junit.Test

import org.junit.Rule
import org.junit.rules.ErrorCollector
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class CharacterUseCaseTest {

    @get:Rule
    val collectorRule = ErrorCollector()

    private val mainCoroutineRule = MainCoroutineRule()

    private val characterRepository: CharacterRepository = FakeCharacterRepository()
    @Test
    fun loadCharacter_CheckValueTest() = mainCoroutineRule.runTest{
        val loadCharacterResults = LoadCharacterUseCase(characterRepository, DataToMarvelMapper())(Unit)
            .getOrNull()?.asSnapshot()

        println("loadCharacterResults : $loadCharacterResults")

        assertNotNull(loadCharacterResults)
        assertEquals(1, loadCharacterResults.size)
        assertEquals(FakeCharacterRepository.TEST_ID, loadCharacterResults.first().id)
    }
}