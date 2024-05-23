package com.example.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API

@Suppress("UnstableApiUsage")
class ApplicationIssueRegistry : IssueRegistry() {
    override val issues = listOf(MapLifecycleDetector.ISSUE)

    override val api: Int
        get() = CURRENT_API

    override val minApi: Int
        get() = 8

    override val vendor: Vendor = Vendor(
        vendorName = "Compose Architecture",
        feedbackUrl = "https://github.com/hadion82/Compose/issues/",
        contact = "https://github.com/hadion82/Compose/issues/"
    )
}
