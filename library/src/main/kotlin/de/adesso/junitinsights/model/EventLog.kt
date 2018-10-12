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
    var currentDate: LocalDateTime = LocalDateTime.now()

    fun log(e: Event) = events.add(e)

    fun writeReport() {
        val json = generateJsonFromEvents()
        val html = insertJsonInTemplate(json)
        val reportFile = writeHtmlToFile(html)
        LoggerFactory.getLogger(this::class.java).debug("Report created at " + reportFile.absolutePath)
    }

    private fun generateJsonFromEvents(): String {
        val report = ReportCreator.createReport(getPageTitle(), events)
        return Gson().toJson(report)
    }

    private fun getPageTitle(): String {
        var pageTitle = "JUnit Insights Report "
        pageTitle += currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))
        return pageTitle
    }

    private fun insertJsonInTemplate(json: String): String {
        val template = InputStreamReader(ClassPathResource("index.html").inputStream).readText()
        return template.replace("var OVERRIDE_REPORT = {}", "var OVERRIDE_REPORT = $json")
    }

    private fun writeHtmlToFile(html: String): File {
        currentDate = LocalDateTime.now()
        val htmlReportFile = File(InsightProperties.reportpath + getReportFileName())
        if (htmlReportFile.parentFile != null)
            htmlReportFile.parentFile.mkdirs()
        PrintWriter(htmlReportFile).use { it.write(html) }
        return htmlReportFile
    }

    private fun getReportFileName(): String {
        var fileName = "JUnit Insights "
        fileName += currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"))
        fileName += ".html"
        return fileName
    }
}
