package com.example.profile

import com.example.test.data.constant.TestConstant.TEST_ID
import com.example.test.domain.profile.FakeGetCharacterProfileUseCaseFactory
import com.example.testing.coroutine.MainCoroutineRule
import com.example.testing.coroutine.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
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
class ProfileViewModelTest {

    private val mainCoroutineRule = MainCoroutineRule()

    private lateinit var profileViewModel: ProfileViewModel

    @Before
    fun setUp() {
        profileViewModel = ProfileViewModel(
            FakeGetCharacterProfileUseCaseFactory(mainCoroutineRule.testDispatcher)
        )
    }
    @Test
    fun getProfile_CheckValue() = mainCoroutineRule.runTest {
        profileViewModel.getCharacterProfile(TEST_ID)
        val character = profileViewModel.profileData.value
        assertEquals(TEST_ID, character?.id)
    }

    @Test
    fun getInvalidProfile_CheckValue() = mainCoroutineRule.runTest {
        profileViewModel.getCharacterProfile(-1)
        val message = profileViewModel.message.value
        assertEquals(Action.Message.FailedToLoadData, message)
    }
}