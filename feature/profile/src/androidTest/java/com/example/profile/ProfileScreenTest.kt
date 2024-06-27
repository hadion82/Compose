package com.example.profile

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import com.example.test.data.constant.TestConstant.TEST_ID
import com.example.test.domain.bookmark.FakeLoadBookmarkUseCaseFactory
import com.example.test.domain.bookmark.FakeRemoveBookmarkUseCaseFactory
import com.example.test.domain.image.FakeSaveThumbnailUseCaseFactory
import com.example.test.domain.profile.FakeGetCharacterProfileUseCaseFactory
import com.example.testing.coroutine.MainCoroutineRule
import com.example.testing.coroutine.runTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest

import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ProfileScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

//    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun profileScreenTest() = mainCoroutineRule.runTest {
        val profileViewModel = ProfileViewModel(
            FakeGetCharacterProfileUseCaseFactory(mainCoroutineRule.testDispatcher)
        )
        composeRule.setContent {
            ProfileScreen(
                uiState = ComposeProfileUiState(uiState = profileViewModel)
            )
        }
        profileViewModel.getCharacterProfile(TEST_ID)
        delay(2000)
        val testId = profileViewModel.profileData.value?.id

        composeRule.onNodeWithTag(ProfileTag.Bar.PROFILE_BAR).assertExists()
        composeRule.onNodeWithTag(ProfileTag.Profile.PROFILE_THUMBNAIL + testId).assertExists()
        composeRule.onNodeWithTag(ProfileTag.Profile.PROFILE_INFORMATION + testId).assertExists()
    }
}