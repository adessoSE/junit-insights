package de.adesso.junitinsights.junit

import de.adesso.junitinsights.annotations.NoJUnitInsights
import de.adesso.junitinsights.tools.InsightProperties
import de.adesso.junitinsights.tools.TimestampWriter
import org.junit.jupiter.api.extension.*
import org.junit.platform.commons.support.AnnotationSupport.isAnnotated


/**
 * Extension that measures the execution time of each test class and method.
 *
 * It implements the callback-functions of the JUnit5 Jupiter API.
 */
class InsightExtension :
        BeforeAllCallback, AfterAllCallback,
        BeforeEachCallback, AfterEachCallback,
        BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private val timestampWriter = TimestampWriter
    private val insightProperties = InsightProperties

    /**
     * Triggered at the very beginning of each test class.
     * @param context Context provided by JUnit 5
     * @see BeforeAllCallback
     */
    override fun beforeAll(context: ExtensionContext) {
        insightProperties.setConfiguration(context)
        if (shouldNotBeBenchmarked(context)) {
            return
        }
        timestampWriter.writeTimestamp(System.currentTimeMillis(),
                "before all",
                context.displayName,
                getMethodName(context))
    }

    /**
     * Triggered after all test methods have finished for a test class.
     * @param context Context provided by JUnit 5
     * @see AfterAllCallback
     */
    override fun afterAll(context: ExtensionContext) {
        if (shouldNotBeBenchmarked(context)) {
            return
        }
        timestampWriter.writeTimestamp(System.currentTimeMillis(),
                "after all",
                context.displayName,
                getMethodName(context))
    }

    /**
     * Triggered before the execution of each test method.
     * @param context Context provided by JUnit 5
     * @see BeforeEachCallback
     */
    override fun beforeEach(context: ExtensionContext) {
        if (shouldNotBeBenchmarked(context)) {
            return
        }
        timestampWriter.writeTimestamp(System.currentTimeMillis(),
                "before each",
                context.displayName,
                getMethodName(context))
    }

    /**
     * Triggered after the execution of each test method.
     * @param context Context provided by JUnit 5
     * @see AfterEachCallback
     */
    override fun afterEach(context: ExtensionContext) {
        if (shouldNotBeBenchmarked(context)) {
            return
        }
        timestampWriter.writeTimestamp(System.currentTimeMillis(),
                "after each",
                context.displayName,
                getMethodName(context))
    }

    /**
     * Triggered right before the execution of a test method.
     * Very similar to beforeEach.
     * @param context Context provided by JUnit 5
     * @see BeforeTestExecutionCallback
     * @see beforeEach
     */
    @Throws(Exception::class)
    override fun beforeTestExecution(context: ExtensionContext) {
        if (shouldNotBeBenchmarked(context)) {
            return
        }
        timestampWriter.writeTimestamp(System.currentTimeMillis(),
                "before test execution",
                context.displayName,
                getMethodName(context))
    }

    /**
     * Triggered right after the execution of a test method.
     * Very similar to afterEach.
     * @param context Context provided by JUnit 5
     * @see AfterTestExecutionCallback
     */
    @Throws(Exception::class)
    override fun afterTestExecution(context: ExtensionContext) {
        if (shouldNotBeBenchmarked(context)) {
            return
        }
        timestampWriter.writeTimestamp(System.currentTimeMillis(),
                "after test execution",
                context.displayName,
                getMethodName(context),
                context.executionException.isPresent)
    }

    /**
     * Checks if @NoJUnitInsights is present and disables measuring then.
     * @param context Context provided by JUnit 5
     * @see NoJUnitInsights
     */
    private fun shouldNotBeBenchmarked(context: ExtensionContext): Boolean {
        return context.element
                .map<Boolean> { el -> isAnnotated(el, NoJUnitInsights::class.java) }
                .orElse(false)
    }

    /**
     * Helper function to remove the class and package names in front of the method name.
     * @param testContext Context provided by JUnit 5
     */
    private fun getMethodName(testContext: ExtensionContext): String {
        if (testContext.testMethod.toString() == "Optional.empty")
            return ""

        return testContext.displayName
    }
}
