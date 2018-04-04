package de.adesso.junitinsights.tools

import org.apache.commons.io.FileUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

var deltaMode = false
var logOutput = false

object TimestampWriter {
    private var currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))
    private var lastTimestamp: Long = 0
    private var timestamps = StringBuilder()

    private var logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun writeTimestamp(timestamp: Long, event: String, testClass: String, testFunction: String) {
        var tstamp: Long = timestamp
        if (deltaMode) {
            if (lastTimestamp == 0.toLong()) {
                lastTimestamp = timestamp
            } else {
                tstamp = timestamp - lastTimestamp
                lastTimestamp = timestamp
            }
        }
        timestamps.append(tstamp.toString() + ";" + event + ";" + trimObjectString(testClass) + ";" + trimObjectString(testFunction) + "\\n\" +\n\"")
        if (logOutput)
            logger.info("########" + tstamp.toString() + ";" + event + ";" + trimObjectString(testClass) + ";" + trimObjectString(testFunction) + "\n")
    }

    fun createReport() {
        val htmlTemplateFile = File("visualization/index.html")
        var htmlString = FileUtils.readFileToString(htmlTemplateFile,"UTF-8")
        htmlString = htmlString.replace("\$timestampCsvString", timestamps.toString())
        val reportPath = "insight_$currentTime.html"
        val htmlReportFile = File(reportPath)
        FileUtils.writeStringToFile(htmlReportFile, htmlString, "UTF-8")
    }

    private fun trimObjectString(string: String): String {
        return string.replace("Optional.empty", "")
                .replace("Optional", "")
                .replace("[", "")
                .replace("]", "")
    }
}
