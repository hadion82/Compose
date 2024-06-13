package com.example.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.test.fio.FakeImageDownloader
import com.example.test.fio.FakeMediaStore
import com.example.data.repository.ImageStreamRepository
import com.example.data.repository.ImageStreamRepositoryImpl
import com.example.testing.coroutine.MainCoroutineRule
import com.example.testing.coroutine.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
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
internal class ImageStreamRepositoryTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var context: Context

    private val imageStreamRepository: ImageStreamRepository =
        ImageStreamRepositoryImpl(
            FakeImageDownloader(), FakeMediaStore()
        )


    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun getPagingData_CheckValue() = mainCoroutineRule.runTest {
        val uri = imageStreamRepository.download(context, "testUri", "testDir", "testFile.jpg")
        assertEquals("downloadFile", uri.toString())
    }
}