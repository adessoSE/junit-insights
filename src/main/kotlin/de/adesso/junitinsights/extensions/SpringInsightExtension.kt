package de.adesso.junitinsights.extensions

import org.junit.jupiter.api.extension.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SpringInsightExtension(
) : BeforeAllCallback, AfterAllCallback,
        BeforeEachCallback, AfterEachCallback,
        BeforeTestExecutionCallback, AfterTestExecutionCallback {

    companion object {
        var logger: Logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun beforeAll(context: ExtensionContext) {
        //TODO Instanciate applicationContextEventService here, before container starts or tests are executed.
        //TODO Also put the object in the extensioncontext, so its accessible in the testcontainer
        logger.info("### beforeAll: class - ${context.testClass} ### method - ${context.testMethod} ###")

        //  applicationContextEventService.putEventIntoDatabaseNow(eventType = ApplicationContextEventType.TEST_CLASS_START, context = context)
    }

    override fun afterAll(context: ExtensionContext) {
        logger.info("### afterAll: class - ${context.testClass} ### method - ${context.testMethod} ###")
    }

    override fun beforeEach(context: ExtensionContext) {
        logger.info("### beforeEach: class - ${context.testClass} ### method - ${context.testMethod} ###")
    }

    override fun afterEach(context: ExtensionContext) {
        logger.info("### afterEach: class - ${context.testClass} ### method - ${context.testMethod} ###")
    }

    override fun beforeTestExecution(context: ExtensionContext) {
        logger.info("### beforeTestExecution: class - ${context.testClass} ### method - ${context.testMethod} ###")
    }

    override fun afterTestExecution(context: ExtensionContext) {
        logger.info("### afterTestExecution: class - ${context.testClass} ### method - ${context.testMethod} ###")
    }

}
