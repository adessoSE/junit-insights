package de.adesso.junitinsights.model

import com.google.gson.Gson
import de.adesso.junitinsights.tools.InsightProperties
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*

class EventLogTest {

    @Test
    fun basicLoggingTest() {
        InsightProperties.enabled = true
        InsightProperties.reportpath = "test-reports/"

        createTestEvents()

        val dir = File(InsightProperties.reportpath)
        if (dir.exists()) {
            dir.listFiles().forEach {
                it.delete()
            }
        }

        EventLog.writeReport()
        val firstFile = dir.listFiles().first()
        assertTrue(firstFile.exists())

        val json = extractJsonFromFile(firstFile)
        assertNotEquals("", json)

        val report = Gson().fromJson<Report>(json, Report::class.java)
        assertNotNull(report)

        assertTrue(report.projectName.startsWith("JUnit Insights Report"))
        assertEquals(1, report.springContextsCreated)
        assertEquals(1, report.testClasses.count())
        assertEquals("test-class", report.testClasses.first().name)
        assertEquals(0, report.testClasses.first().firstTimestamp)
        assertEquals(1, report.testClasses.first().methods.count())
        assertEquals(2, report.testClasses.first().beforeAll)
        assertEquals(3, report.testClasses.first().before)
        assertEquals(4, report.testClasses.first().exec)
        assertEquals(5, report.testClasses.first().after)
        assertEquals(6, report.testClasses.first().afterAll)
        assertEquals(0, report.testClasses.first().between)
        assertEquals(1, report.testClasses.first().spring)
        assertEquals("test-method", report.testClasses.first().methods.first().name)
        assertEquals(1, report.testClasses.first().methods.first().firstTimestamp)
        assertEquals(3, report.testClasses.first().methods.first().before)
        assertEquals(4, report.testClasses.first().methods.first().exec)
        assertEquals(5, report.testClasses.first().methods.first().after)
        assertTrue(report.testClasses.first().methods.first().successful)

        InsightProperties.enabled = false
        EventLog.clearEvents()
    }

    @Test
    fun loggingDisabledTest() {
        InsightProperties.enabled = false
        InsightProperties.reportpath = "test-reports/"

        createTestEvents()

        val dir = File(InsightProperties.reportpath)
        if (dir.exists()) {
            dir.listFiles().forEach {
                it.delete()
            }
        }

        EventLog.writeReport()
        val fileCount = dir.listFiles().size
        assertEquals(0, fileCount)

        EventLog.clearEvents()
    }

    private fun createTestEvents() {
        EventLog.log((Event("before all", Date(0), "test-class")))
        EventLog.log(Event("context refreshed", Date(1)))
        EventLog.log(Event("before each", Date(3), "test-class", "test-method"))
        EventLog.log(Event("before test execution", Date(6), "test-class", "test-method"))
        EventLog.log(Event("after test execution", Date(10), "test-class", "test-method"))
        EventLog.log(Event("after each", Date(15), "test-class", "test-method"))
        EventLog.log(Event("after all", Date(21), "test-class"))
    }

    private fun extractJsonFromFile(file: File) : String {
        var json = ""
        file.forEachLine fileLoop@{
            if (it.contains("var OVERRIDE_REPORT")) {
                json = it.substring(it.indexOf("var OVERRIDE_REPORT") + 22)
                println(json)
                return@fileLoop
            }
        }
        return json
    }
}
