package com.example.testing.coroutine

import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

fun TestDispatcher.runTest(block: suspend TestScope.() -> Unit) =
    TestScope(this).runTest {
        block()
    }