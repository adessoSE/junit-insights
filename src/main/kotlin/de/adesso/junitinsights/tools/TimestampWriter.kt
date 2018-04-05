package de.adesso.junitinsights.tools

import com.google.common.io.CharStreams
import de.adesso.junitinsights.config.JUnitInsightsConfiguration
import org.apache.commons.io.FileUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.File
import java.io.InputStreamReader
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
        val htmlTemplateFileInputStream = ClassPathResource(JUnitInsightsConfiguration.reportTemplatePath)
        var htmlString = CharStreams.toString(InputStreamReader(htmlTemplateFileInputStream.inputStream, "UTF-8"))
        htmlString = htmlString.replace("\$timestampCsvString", timestamps.toString())
        val htmlReportFile = File(JUnitInsightsConfiguration.reportPath + "insight_$currentTime.html")
        FileUtils.writeStringToFile(htmlReportFile, htmlString, "UTF-8")
    }

    private fun trimObjectString(string: String): String {
        return string.replace("Optional.empty", "")
                .replace("Optional", "")
                .replace("[", "")
                .replace("]", "")
    }
}
