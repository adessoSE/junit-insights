package de.adesso.junitinsights.model

import com.google.gson.Gson
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
    var events: ArrayList<Event> = ArrayList()

    fun log(e: Event) = events.add(e)

    fun writeReport() {
        val json = generateJsonFromEvents()
        val html = insertJsonInTemplate(json)
        val reportFile = writeHtmlToFile(html)
        LoggerFactory.getLogger(this::class.java).debug("Report created at " + reportFile.absolutePath)
    }

    private fun generateJsonFromEvents(): String {
        val report = ReportCreator.createReport("JUnit Insights Report ${getFormattedDateTimeString()}", events)
        return Gson().toJson(report)
    }

    private fun insertJsonInTemplate(json: String): String {
        val template = InputStreamReader(ClassPathResource("index.html").inputStream).readText()
        return template.replace("var OVERRIDE_REPORT = {}", "var OVERRIDE_REPORT = $json")
    }

    private fun getFormattedDateTimeString(): String =
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))

    private fun writeHtmlToFile(html: String): File {
        val currentTime = getFormattedDateTimeString()
        val htmlReportFile = File(InsightProperties.reportpath + "insight_$currentTime.html")
        if (htmlReportFile.parentFile != null)
            htmlReportFile.parentFile.mkdirs()
        PrintWriter(htmlReportFile).use { it.write(html) }
        return htmlReportFile
    }
}
