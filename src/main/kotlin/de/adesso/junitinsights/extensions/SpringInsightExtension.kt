package de.adesso.junitinsights.extensions

import de.adesso.junitinsights.EventType
import de.adesso.junitinsights.TimestampEventService
import org.junit.jupiter.api.extension.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.annotation.Resource

@ComponentScan
@Component
open class SpringInsightExtension : SpringExtension() {
    companion object {
        var logger : Logger = LoggerFactory.getLogger(this::class.java)
    }

    @Resource
    lateinit var timestampEventService: TimestampEventService

    override fun beforeAll(context: ExtensionContext) {
        super.beforeAll(context)
        logger.info("### beforeAll: class - " + context.testClass + " ###"
                + " method - " + context.testMethod + " ###")
        //timestampEventService.createEventNow(eventType = EventType.TEST_CLASS_START, context = context)
        //TODO Adjust
    }

    override fun afterAll(context: ExtensionContext) {
        super.afterAll(context)
        logger.info("### afterAll: class - " + context.testClass + " ###"
                + " method - " + context.testMethod + " ###")
        //timestampEventService.createEventNow(eventType = EventType.TEST_CLASS_END, context = context)
        //TODO Adjust
    }

    override fun beforeEach(context: ExtensionContext) {
        super.beforeEach(context)
        logger.info("### beforeEach: class - " + context.testClass + " ###"
                + " method - " + context.testMethod + " ###")
        timestampEventService.createEventNow(eventType = EventType.TEST_METHOD_START, context = context)
    }

    override fun afterEach(context: ExtensionContext) {
        super.afterEach(context)
        logger.info("### afterEach: class - " + context.testClass + " ###"
                + " method - " + context.testMethod + " ###")
    }

    override fun beforeTestExecution(context: ExtensionContext) {
        super.beforeTestExecution(context)
        logger.info("### beforeTestExecution: class - " + context.testClass + " ###"
                + " method - " + context.testMethod + " ###")
    }

    override fun afterTestExecution(context: ExtensionContext) {
        super.afterTestExecution(context)
        logger.info("### afterTestExecution: class - " + context.testClass + " ###"
                + " method - " + context.testMethod + " ###")
    }

}
