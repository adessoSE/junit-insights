package de.adesso.junitinsights.extensions

import org.junit.jupiter.api.extension.*
import org.springframework.test.context.junit.jupiter.SpringExtension

open class SpringInsightExtension : SpringExtension() {
    override fun beforeAll(context: ExtensionContext) {
        super.beforeAll(context)
        println("### beforeAll called ###")
    }

    override fun afterAll(context: ExtensionContext) {
        super.afterAll(context)
        println("### afterAll called ###")
    }

    override fun beforeEach(context: ExtensionContext) {
        super.beforeEach(context)
        println("### beforeEach called ###")
    }

    override fun afterEach(context: ExtensionContext) {
        super.afterEach(context)
        println("### afterEach called ###")
    }

    override fun beforeTestExecution(context: ExtensionContext) {
        super.beforeTestExecution(context)
        println("### beforeTestExecution called ###")
    }

    override fun afterTestExecution(context: ExtensionContext) {
        super.afterTestExecution(context)
        println("### afterTestExecution called ###")
    }

}