package de.adesso.junitinsights.tests

import de.adesso.junitinsights.annotations.NoJUnitInsights
import de.adesso.junitinsights.tools.TimestampWriter
import org.junit.jupiter.api.extension.*
import org.junit.platform.commons.support.AnnotationSupport.isAnnotated


/**
 * Extension that measures the execution time of each test class and method
 *
 */
class TestBenchmarkExtension :
        BeforeAllCallback, AfterAllCallback,
        BeforeEachCallback, AfterEachCallback,
        BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private val timestampWriter = TimestampWriter

    override fun beforeAll(context: ExtensionContext) {
        if (shouldNotBeBenchmarked(context)) {
            return
        }
        timestampWriter.writeTimestamp(System.currentTimeMillis(),
                "before all",
                context.testClass.toString(),
                context.testMethod.toString())
    }

    override fun afterAll(context: ExtensionContext) {
        if (shouldNotBeBenchmarked(context)) {
            return
        }
        timestampWriter.writeTimestamp(System.currentTimeMillis(),
                "after all",
                context.testClass.toString(),
                context.testMethod.toString())
    }

    override fun beforeEach(context: ExtensionContext) {
        if (shouldNotBeBenchmarked(context)) {
            return
        }
        timestampWriter.writeTimestamp(System.currentTimeMillis(),
                "before each",
                context.testClass.toString(),
                context.testMethod.toString())
    }

    override fun afterEach(context: ExtensionContext) {
        if (shouldNotBeBenchmarked(context)) {
            return
        }
        timestampWriter.writeTimestamp(System.currentTimeMillis(),
                "after each",
                context.testClass.toString(),
                context.testMethod.toString())
    }

    @Throws(Exception::class)
    override fun beforeTestExecution(context: ExtensionContext) {
        if (shouldNotBeBenchmarked(context)) {
            return
        }
        timestampWriter.writeTimestamp(System.currentTimeMillis(),
                "before test execution",
                context.testClass.toString(),
                context.testMethod.toString())
    }

    @Throws(Exception::class)
    override fun afterTestExecution(context: ExtensionContext) {
        if (shouldNotBeBenchmarked(context)) {
            return
        }
        timestampWriter.writeTimestamp(System.currentTimeMillis(),
                "after test execution",
                context.testClass.toString(),
                context.testMethod.toString())
    }

    private fun shouldNotBeBenchmarked(context: ExtensionContext): Boolean {
        return context.element
                .map<Boolean> { el -> isAnnotated(el, NoJUnitInsights::class.java) }
                .orElse(false)
    }
}