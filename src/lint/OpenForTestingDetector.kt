class OpenForTestingDetector: Detector(), Detector.UastScanner {
    companion object {
        @JvmField
        val ISSUE: Issue = Issue.create(
                "OpenForTesting",
                "Subclassing Open For Testing class",
                "This check highlights when you subclass a class that has been opened for testing" +
                        "only. It was not intended to be subclassed, so if you require this class " +
                        "to be open properly, you should update the class itself to note that it is" +
                        "open.",
                Category.LINT,
                6,
                Severity.ERROR,
                Implementation(
                        OpenForTestingDetector::class.java,
                        Scope.JAVA_FILE_SCOPE
                )
        )
        private const val OPEN_FOR_TESTING_ANNOTATION = "@OpenForTesting"
    }

    private fun reportMessage(superClassName: String?) =
            "${superClassName}を勝手に継承するな!"

    override fun getApplicableUastTypes(): MutableList<Class<out UElement>> {
        return mutableListOf(UClass::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitClass(node: UClass) {
                val superClass = node.superClass ?: return

                // Check to see if our superclass is annotated with the testing annotation
                val superclassHasAnnotation = superClass.annotations.any {
                    it.psi?.text?.equals(OPEN_FOR_TESTING_ANNOTATION) == true
                }

                // We found a violation, so highlight the class as being invalid
                if (superclassHasAnnotation) {
                    context.run {
                        report(ISSUE, node, getNameLocation(node),
                                reportMessage(superClass.name))
                    }
                }
            }
        }
    }
}
