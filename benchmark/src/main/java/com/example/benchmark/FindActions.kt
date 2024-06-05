package com.example.benchmark

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

fun MacrobenchmarkScope.getTopAppBar(): UiObject2 {
    device.wait(Until.hasObject(By.text("Bookmarks")), 2_000)
    return device.findObject(By.text("Bookmarks"))
}