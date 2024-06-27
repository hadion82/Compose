package com.example.benchmark.bookmarks

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.example.benchmark.PACKAGE_NAME
import com.example.benchmark.getTopAppBar
import com.example.benchmark.waitAndFindObject

fun MacrobenchmarkScope.goToBookmarksScreen() {
    val bookmarkSelector = By.text("Bookmark")
    val bookmarkButton = device.waitAndFindObject(bookmarkSelector, 5_000)
    bookmarkButton.click()
    device.waitForIdle()
    getTopAppBar()
}
