package de.adesso.junitinsights.tools

import com.google.gson.Gson
import de.adesso.junitinsights.model.EventLog
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import java.io.File
import java.io.InputStreamReader
import java.io.PrintWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TimestampWriter {
    fun writeReport() {
        val report = ReportCreator.createReport("test", EventLog.events)
        val jsonString = Gson().toJson(report)

        val template = InputStreamReader(ClassPathResource("dist/index.html").inputStream).readText()
        val reportHTML = template.replace("var OVERRIDE_REPORT = {}", "var OVERRIDE_REPORT = $jsonString")

        val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))
        val htmlReportFile = File(InsightProperties.reportpath + "insight_$currentTime.html")
        if (htmlReportFile.parentFile != null)
            htmlReportFile.parentFile.mkdirs()

        PrintWriter(htmlReportFile).use { it.write(reportHTML) }

        LoggerFactory.getLogger(this::class.java).debug("Report created at " + htmlReportFile.absolutePath)
    }
}
