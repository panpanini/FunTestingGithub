package com.mercari.lint

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import org.intellij.lang.annotations.Language
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths


class OpenForTestingDetectorTest: LintDetectorTest() {

    companion object {
        private val ktAssetsDir = File("Lint/src/test/kt-assets")
    }


    fun testKotlin() {
        val openForTestingSuperclass = getContent(ktAssetsDir, filename = "OpenForTestingSuperclass.kt")
        val openForTestingSubclass = getContent(ktAssetsDir, "OpenForTestingSubclass.kt")
        val annotation = getContent(ktAssetsDir, "shadow/OpenForTesting.kt")

        lint().files(
                kotlin(openForTestingSuperclass),
                kotlin(openForTestingSubclass),
                kotlin(annotation)
        ).allowCompilationErrors(false)
                .run()
                .expect("src/OpenForTestingSubclass.kt:1: Error: OpenForTestingSuperclass is only open for testing, and is not intended to be subclassed [OpenForTesting]\n" +
                        "class OpenForTestingSubclass : OpenForTestingSuperclass()\n" +
                        "      ~~~~~~~~~~~~~~~~~~~~~~\n" +
                        "1 errors, 0 warnings")
    }


    override fun getDetector(): Detector = OpenForTestingDetector()

    override fun getIssues(): MutableList<Issue> {
        return mutableListOf(OpenForTestingDetector.ISSUE)
    }


    private fun getContent(dir: File, filename: String): String {
        return String(
                Files.readAllBytes(Paths.get(File(dir, filename).toURI()))
        )
    }


}