package com.example.benchmark.bookmarks

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import com.example.benchmark.getTopAppBar

fun MacrobenchmarkScope.goToBookmarksScreen() {
    val bookmarkSelector = By.res("go_to_bookmark")
    val bookmarkButton = device.findObject(bookmarkSelector)
    bookmarkButton.click()
    device.waitForIdle()
    getTopAppBar()
}
