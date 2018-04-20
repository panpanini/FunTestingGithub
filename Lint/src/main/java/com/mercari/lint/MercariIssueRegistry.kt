package com.mercari.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue

class MercariIssueRegistry: IssueRegistry() {
    override val issues: List<Issue>
        get() = listOf(OpenForTestingDetector.ISSUE)
}