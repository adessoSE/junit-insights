package de.adesso.junitinsights.model

import com.google.gson.Gson
import de.adesso.junitinsights.tools.IReportCreator
import de.adesso.junitinsights.tools.InsightProperties
import de.adesso.junitinsights.tools.ReportCreator
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import java.io.File
import java.io.InputStreamReader
import java.io.PrintWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object EventLog {
    private var events: ArrayList<Event> = ArrayList()
    private var currentDate: LocalDateTime = LocalDateTime.now()
    var reportCreator: IReportCreator = ReportCreator

    fun log(e: Event) {
        // If JUnit Insights is disabled, no events have to be logged
        if (!InsightProperties.enabled)
            return

        events.add(e)
    }

    fun writeReport() {
        // If JUnit Insights is disabled, the report should not be created
        if (!InsightProperties.enabled)
            return

        currentDate = LocalDateTime.now() // Reset time to accommodate time between EventLog creation and writing
        val json = generateJsonFromEvents()
        val html = insertJsonInTemplate(json)
        val reportFile = writeHtmlToFile(html)
        LoggerFactory.getLogger(this::class.java).debug("Report created at ${reportFile.absolutePath}")
    }

    fun clearEvents() {
        events.clear()
    }

    fun eventCount() = events.count()

    private fun generateJsonFromEvents(): String {
        val report = reportCreator.createReport(getPageTitle(), events)
        return Gson().toJson(report)
    }

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
