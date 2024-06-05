package com.example.domain

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.BookmarkRepository
import com.example.domain.usecase.bookmark.AddBookmarkUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.ErrorCollector
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
class BookmarkUnitTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val collectorRule = ErrorCollector()

    private val dispatcher = StandardTestDispatcher()

    private lateinit var context: Context

    @Inject
    lateinit var bookmarkRepository: BookmarkRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        hiltRule.inject()
    }

    @Test
    fun addBookmarkUseCaseTest() {
        AddBookmarkUseCase(dispatcher, bookmarkRepository)
    }
}