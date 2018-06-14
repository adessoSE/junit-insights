package de.adesso.junitinsights.junit

import de.adesso.junitinsights.tools.TimestampWriter
import org.junit.platform.launcher.TestExecutionListener
import org.junit.platform.launcher.TestPlan

class InsightListener : TestExecutionListener {
    private val timestampWriter = TimestampWriter

    override fun testPlanExecutionFinished(testPlan: TestPlan) {
        timestampWriter.createReport()
    }
}