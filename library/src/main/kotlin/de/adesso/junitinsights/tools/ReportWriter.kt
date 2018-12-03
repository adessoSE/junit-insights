package de.adesso.junitinsights.tools

import com.google.gson.Gson
import de.adesso.junitinsights.model.Report
import org.slf4j.LoggerFactory
import java.io.File
import java.io.InputStreamReader
import java.io.PrintWriter
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object ReportWriter : IReportWriter {

    private var currentDate: LocalDateTime = LocalDateTime.now()

    override fun writeReport(report: Report) {
        // If JUnit Insights is disabled, the report should not be created
        if (!InsightProperties.enabled)
            return
        currentDate = report.created.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
        val json = generateJsonFromReport(report)
        val html = insertJsonInTemplate(json)
        val reportFile = writeHtmlToFile(html)
        LoggerFactory.getLogger(this::class.java).debug("Report created at ${reportFile.absolutePath}")
    }

    private fun generateJsonFromReport(report: Report): String = Gson().toJson(report)

    private fun insertJsonInTemplate(json: String): String {
        val template = InputStreamReader(this.javaClass.getResourceAsStream("/index.html")).readText()
        return template.replace("var OVERRIDE_REPORT = {}", "var OVERRIDE_REPORT = $json")
    }

    private fun writeHtmlToFile(html: String): File {
        val htmlReportFile = File("${InsightProperties.reportpath}${getReportFileName()}")
        if (htmlReportFile.parentFile != null)
            htmlReportFile.parentFile.mkdirs()
        PrintWriter(htmlReportFile).use { it.write(html) }
        return htmlReportFile
    }

    private val filenameDatePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
    private val titleDatePattern = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")

    private fun getPageTitle() = "JUnit Insights Report ${currentDate.format(titleDatePattern)}"
    private fun getReportFileName() = "JUnit_Insights_${currentDate.format(filenameDatePattern)}.html"
}
