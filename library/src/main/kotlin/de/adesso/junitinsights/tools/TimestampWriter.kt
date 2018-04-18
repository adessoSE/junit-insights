package de.adesso.junitinsights.tools

import de.adesso.junitinsights.config.JUnitInsightsReportProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import java.io.File
import java.io.InputStreamReader
import java.io.PrintWriter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * Configuration options for the logging of timestamps
 */
var deltaMode = false   // generates data with relative timestamps instead of absolute timestamps
var logOutput = false   // adds logging messages when saving a csv timestamp

/**
 * Singleton class that provides functions to save timestamps for events with the corresponding data.
 * This also includes the generation of the final html report file containing the collected data.
 */
object TimestampWriter {
    private var currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))
    private var lastTimestamp: Long = 0
    private var timestamps = StringBuilder()
    private var logger: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Writes a timestamp with some meta information into the buffer.
     * @param timestamp Timestamp for the event
     * @param event Event that triggered the creation of this timestamp
     * @param testClass In case of an event that belongs to a certain test class, this can be included here
     * @param testFunction In case of an event that belongs to a certain test function, this can be included here
     */
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

    /**
     * Creates the html file containing all the collected data.
     */
    fun createReport() {
        val htmlTemplatePath = ClassPathResource(JUnitInsightsReportProperties.templatepath)
        var htmlString = InputStreamReader(htmlTemplatePath.inputStream, "UTF-8").readText()
        htmlString = htmlString.replace("\$timestampCsvString", timestamps.toString())
        val htmlReportFile = File(JUnitInsightsReportProperties.path + "insight_$currentTime.html")
        htmlReportFile.parentFile.mkdirs()
        PrintWriter(htmlReportFile).use {
            it.write(htmlString);
        }

    }

    /**
     * Removes unnecessary characters from class and method names.
     * @param string The string containing information about the class or method
     */
    private fun trimObjectString(string: String): String {
        return string.replace("Optional.empty", "")
                .replace("Optional", "")
                .replace("[", "")
                .replace("]", "")
    }
}
