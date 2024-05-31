

package com.example.benchmark.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import com.example.benchmark.PACKAGE_NAME
import com.example.benchmark.bookmarks.goToBookmarksScreen
import org.junit.Rule
import org.junit.Test

class BookmarksBaselineProfile {
    @get:Rule val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() =
        baselineProfileRule.collect(PACKAGE_NAME) {
            startActivityAndWait()
            goToBookmarksScreen()
        }
}
