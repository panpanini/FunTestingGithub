class OpenForTestingDetectorTest: LintDetectorTest() {
    fun testOpenForTestingDetector() {
        lint().files(
                kotlin(openForTestingSuperclass.kt),
                kotlin(openForTestingSubclass.kt),
                kotlin(annotation.kt)
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
}
