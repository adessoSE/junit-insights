package de.adesso.junitinsights.junit

import de.adesso.junitinsights.annotations.NoJUnitInsights
import de.adesso.junitinsights.model.Event
import de.adesso.junitinsights.model.EventLog
import de.adesso.junitinsights.tools.*
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

    private val reportWriter: IReportWriter = ReportWriter
    private val reportCreator: IReportCreator = ReportCreator

    /**
     * These methods get called at certain events in the JUnit test plan execution.
     * See the documentation of the callbacks for further information.
     * These events are tracked to extract the information we are looking for.
     *
     * @see <a href="https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/api/extension/package-summary.html">JUnit Jupiter API extension</a>
     */

    /**
     * "Callback that is invoked once before all tests in the current container." (from documentation)
     * @see <a href="https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/api/extension/BeforeAllCallback.html">Documentation</a>
     */
    override fun beforeAll(context: ExtensionContext) {
        InsightProperties.setConfiguration(context)
        saveTimestamp("before all", context)
    }

    /**
     * "Callback that is invoked once after all tests in the current container." (from documentation)
     * @see <a href="https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/api/extension/AfterAllCallback.html">Documentation</a>
     */
    override fun afterAll(context: ExtensionContext) = saveTimestamp("after all", context)

    /**
     * "Callback that is invoked before each test is invoked." (from documentation)
     * @see <a href="https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/api/extension/BeforeEachCallback.html">Documentation</a>
     */
    override fun beforeEach(context: ExtensionContext) = saveTimestamp("before each", context)

    /**
     * "Callback that is invoked after each test has been invoked." (from documentation)
     * @see <a href="https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/api/extension/AfterEachCallback.html">Documentation</a>
     */
    override fun afterEach(context: ExtensionContext) = saveTimestamp("after each", context)

    /**
     * "Callback that is invoked immediately before each test is executed." (from documentation)
     * @see <a href="https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/api/extension/BeforeTestExecutionCallback.html">Documentation</a>
     */
    override fun beforeTestExecution(context: ExtensionContext) = saveTimestamp("before test execution", context)

    /**
     * "Callback that is invoked immediately after each test has been executed." (from documentation)
     * @see <a href="https://junit.org/junit5/docs/5.0.2/api/org/junit/jupiter/api/extension/AfterTestExecutionCallback.html">Documentation</a>
     */
    override fun afterTestExecution(context: ExtensionContext) = saveTimestamp("after test execution", context, context.executionException.isPresent)

    /**
     * Gets called after the complete test plan has been executed, so the report can be generated.
     */
    override fun testPlanExecutionFinished(testPlan: TestPlan) {
        val report = reportCreator.createReport(EventLog.events)
        reportWriter.writeReport(report)
    }

    private fun saveTimestamp(event: String, context: ExtensionContext, testFailing: Boolean = false) {
        if (shouldNotBeBenched(context))
            return

        EventLog.log(Event(event, Date(), getClassName(context), getMethodName(context), !testFailing))
    }

    /**
     * Checks if @NoJUnitInsights is present and disables measuring then.
     * @param context Context provided by JUnit 5
     * @see NoJUnitInsights
     */
    private fun shouldNotBeBenched(context: ExtensionContext) =
            context.element
                    .map<Boolean> { el -> isAnnotated(el, NoJUnitInsights::class.java) }
                    .orElse(false)

    /**
     * Helper function to get the displayName of the test class, if a class is present.
     * @param context Context provided by JUnit 5
     */
    private fun getMethodName(context: ExtensionContext) =
            if (context.testMethod.toString() == "Optional.empty") "" else context.displayName

    /**
     * Helper function to remove the package names in front of the class name.
     * @param context Context provided by JUnit 5
     */
    private fun getClassName(context: ExtensionContext) =
            context.testClass.get().toString().substringAfterLast(".")
}
