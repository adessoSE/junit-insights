package de.adesso.junitinsights.tests

import de.adesso.junitinsights.annotations.NoJUnitInsights
import org.junit.jupiter.api.extension.*
import java.util.logging.Logger
import java.util.Collections.singletonMap
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.platform.commons.support.AnnotationSupport.isAnnotated


/**
 * Extension that measures the executiontime of each Testclass and Method
 *
 */
class TestBenchmarkExtension :
        BeforeAllCallback, AfterAllCallback,
        BeforeEachCallback, AfterEachCallback,
        BeforeTestExecutionCallback, AfterTestExecutionCallback {

    companion object {
        private val logger = Logger.getLogger(this::class.java.name)
        private val NAMESPACE = ExtensionContext.Namespace.create("de", "adesso", "JUnitInsights")
    }


    private enum class LaunchTimeKey {
        BEFORE_TEST_EXECUTION , BEFORE_EACH, BEFORE_ALL
    }

    /**
     * //TODO Wichtig, Test-Method is not present in the current Extensioncontext
     */
    override fun beforeAll(context: ExtensionContext) {
        if (shouldNotBeBenchmarked(context)) { return }
        logger.info("### beforeAll: class - ${context.testClass} ### method - ${context.testMethod} ###")
       // getStore(context).put(START_TIME_BEFORE_ALL, System.currentTimeMillis())

    }

    override fun afterAll(context: ExtensionContext) {
        if (shouldNotBeBenchmarked(context)) { return }
        logger.info("### afterAll: class - ${context.testClass} ### method - ${context.testMethod} ###")
        //calculateTimeFor(START_TIME_BEFORE_ALL, context = context)

    }

    override fun beforeEach(context: ExtensionContext) {
        if (shouldNotBeBenchmarked(context)) { return }
        getStore(context).put(LaunchTimeKey.BEFORE_EACH, System.currentTimeMillis())
        logger.info("### beforeEach: class - ${context.testClass} ### method - ${context.testMethod} ###")

    }

    override fun afterEach(context: ExtensionContext) {
        if (shouldNotBeBenchmarked(context)) { return }
        logger.info("### afterEach: class - ${context.testClass} ### method - ${context.testMethod} ###")
        calculateTimeFor(LaunchTimeKey.BEFORE_EACH, context = context)
    }

    @Throws(Exception::class)
    override fun beforeTestExecution(context: ExtensionContext) {
        if (shouldNotBeBenchmarked(context)) { return }
        getStore(context).put(LaunchTimeKey.BEFORE_TEST_EXECUTION, System.currentTimeMillis())
        logger.info("### beforeTestExecution: class - ${context.testClass} ### method - ${context.testMethod} ###")
    }

    @Throws(Exception::class)
    override fun afterTestExecution(context: ExtensionContext) {
        if (shouldNotBeBenchmarked(context)) { return }
        logger.info("### afterTestExecution: class - ${context.testClass} ### method - ${context.testMethod} ###")
        calculateTimeFor(LaunchTimeKey.BEFORE_TEST_EXECUTION, context = context)
    }

    private fun getStore(context: ExtensionContext): ExtensionContext.Store {
        return context.getStore(NAMESPACE)
    }

    private fun calculateTimeFor(key: LaunchTimeKey, context: ExtensionContext) {
        val testMethod = context.requiredTestMethod
        val startTime = getStore(context).remove(key, Long::class.javaPrimitiveType)
        val duration = System.currentTimeMillis() - startTime
        logger.info { String.format("Method [%s] took %s ms.", testMethod.name, duration) }

    }

    private fun shouldNotBeBenchmarked(context: ExtensionContext): Boolean {
        return context.element
                .map<Boolean> { el -> isAnnotated(el, NoJUnitInsights::class.java) }
                .orElse(false)
    }

    private fun report(unit: String, context: ExtensionContext, elapsedTime: Long) {
        val message = String.format("%s '%s' took %d ms.", unit, context.displayName, elapsedTime)
        context.publishReportEntry(singletonMap("Benchmark", message))
    }

}