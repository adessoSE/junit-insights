package de.adesso.junitinsights.junit

import de.adesso.junitinsights.annotations.NoJUnitInsights
import de.adesso.junitinsights.model.Event
import de.adesso.junitinsights.model.EventLog
import de.adesso.junitinsights.tools.InsightProperties
import de.adesso.junitinsights.tools.TimestampWriter
import org.junit.jupiter.api.extension.*
import org.junit.platform.commons.support.AnnotationSupport.isAnnotated
import org.junit.platform.launcher.TestExecutionListener
import org.junit.platform.launcher.TestPlan
import java.util.*


/**
 * Extension that measures the execution time of each test class and method.
 * It implements the callback-functions of the JUnit5 Jupiter API.
 */
class JUnitCallbacks :
        BeforeAllCallback, AfterAllCallback,
        BeforeEachCallback, AfterEachCallback,
        BeforeTestExecutionCallback, AfterTestExecutionCallback,
        TestExecutionListener {

    override fun beforeAll(context: ExtensionContext) {
        InsightProperties.setConfiguration(context)
        saveTimestamp("before all", context)
    }

    override fun testPlanExecutionFinished(testPlan: TestPlan) = TimestampWriter.writeReport()

    override fun afterAll(context: ExtensionContext) = saveTimestamp("after all", context)
    override fun beforeEach(context: ExtensionContext) = saveTimestamp("before each", context)
    override fun afterEach(context: ExtensionContext) = saveTimestamp("after each", context)
    override fun beforeTestExecution(context: ExtensionContext) = saveTimestamp("before test execution", context)
    override fun afterTestExecution(context: ExtensionContext) = saveTimestamp("after test execution", context, context.executionException.isPresent)

    private fun saveTimestamp(event: String, context: ExtensionContext, testFailing: Boolean = false) {
        if (shouldNotBeBenched(context))
            return

        EventLog.log(Event(event, Date(), getClassName(context), getMethodName(context), !testFailing))
    }

    private fun shouldNotBeBenched(context: ExtensionContext) =
            context.element
                    .map<Boolean> { el -> isAnnotated(el, NoJUnitInsights::class.java) }
                    .orElse(false)

    private fun getMethodName(testContext: ExtensionContext) =
            if (testContext.testMethod.toString() == "Optional.empty") "" else testContext.displayName

    private fun getClassName(context: ExtensionContext) =
            context.testClass.get().toString().substringAfterLast(".")
}
