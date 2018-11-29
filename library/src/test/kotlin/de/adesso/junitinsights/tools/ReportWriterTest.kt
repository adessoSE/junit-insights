package de.adesso.junitinsights.tools

import com.google.gson.Gson
import de.adesso.junitinsights.model.Report
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*

class ReportWriterTest {

    @AfterEach
    private fun restoreCleanEnvironment() {
        val dir = File(InsightProperties.reportpath)
        if (dir.exists())
            dir.listFiles()
                    .forEach { it.delete() }
        InsightProperties.enabled = false
        InsightProperties.reportpath = ""
    }

    @Test
    fun writtenReportEqualsOriginalReport() {
        // Arrange
        InsightProperties.enabled = true
        InsightProperties.reportpath = "test-reports/"
        val originalReport = Report("Some Name", Date(), 1, ArrayList())

        // Act
        ReportWriter.writeReport(originalReport)

        // Assert
        val firstFile = File(InsightProperties.reportpath).listFiles().first()
        assertTrue(firstFile.exists())

        val json = extractJsonFromFile(firstFile)
        assertNotEquals("", json)

        val extractedReport = Gson().fromJson<Report>(json, Report::class.java)
        assertEquals(originalReport.projectName, extractedReport.projectName)
        assertEquals(originalReport.springContextsCreated, extractedReport.springContextsCreated)
        assertEquals(originalReport.testClasses, extractedReport.testClasses)
        assertTrue(originalReport.created.time - extractedReport.created.time <= 999) // Rounding of 1 sec is acceptable
    }

    @Test
    fun whenInsightsIsDisabledNoReportIsWritten() {
        // Arrange
        InsightProperties.enabled = false
        InsightProperties.reportpath = "test-reports/"
        val originalReport = Report("Some Name", Date(), 1, ArrayList())

        // Act
        ReportWriter.writeReport(originalReport)

        // Assert
        val dir = File(InsightProperties.reportpath)
        if (dir.exists())
            assertEquals(0, dir.listFiles().size)
    }

    private fun extractJsonFromFile(file: File): String {
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