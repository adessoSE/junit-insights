package de.adesso.junitinsights.extensions

import org.junit.jupiter.api.extension.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.test.context.junit.jupiter.SpringExtension

open class SpringInsightExtension : SpringExtension() {

    companion object {
        var logger : Logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun beforeAll(context: ExtensionContext) {
        super.beforeAll(context)
        logger.info("### beforeAll: class - " + context.testClass + " ###"
                + " method - " + context.testMethod + " ###")
    }

    override fun afterAll(context: ExtensionContext) {
        super.afterAll(context)
        logger.info("### afterAll: class - " + context.testClass + " ###"
                + " method - " + context.testMethod + " ###")
    }

    override fun beforeEach(context: ExtensionContext) {
        super.beforeEach(context)
        logger.info("### beforeEach: class - " + context.testClass + " ###"
                + " method - " + context.testMethod + " ###")
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
