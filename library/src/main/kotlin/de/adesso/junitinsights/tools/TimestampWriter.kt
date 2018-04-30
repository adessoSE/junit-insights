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
 * Singleton class that provides functions to save timestamps for events with the corresponding data.
 * This also includes the generation of the final html report file containing the collected data.
 */
object TimestampWriter {
    private var currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))
    private var timestamps = StringBuilder()
    private var logger: Logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Writes a timestamp with some meta information into the buffer.
     * @param timestamp Timestamp for the event
     * @param event Event that triggered the creation of this timestamp
     * @param testClass In case of an event that belongs to a certain test class, this can be included here
     * @param testFunction In case of an event that belongs to a certain test function, this can be included here
     */
    fun writeTimestamp(timestamp: Long, event: String, testClass: String, testFunction: String, testFailing: Boolean = false) {
	if (testClass != "") {
            testClassLogged = true
        }

        timestamps.append(timestamp.toString() + ";"
                + event + ";"
                + trimObjectString(testClass) + ";"
                + trimObjectString(testFunction) + ";"
                + testFailing
                + "\\n\" +\n\"")
        logger.debug("Timestamp saved: " + timestamp.toString() + ";" + event + ";" + trimObjectString(testClass) + ";" + trimObjectString(testFunction))
    }

    /**
     * Creates the html file containing all the collected data.
     */
    fun createReport() {
        if (!testClassLogged)
            return

        val htmlTemplatePath = ClassPathResource(JUnitInsightsReportProperties.templatepath)
        var htmlString = InputStreamReader(htmlTemplatePath.inputStream, "UTF-8").readText()
        htmlString = htmlString.replace("\$timestampCsvString", timestamps.toString())

        val htmlReportFile = File(JUnitInsightsReportProperties.path + "insight_$currentTime.html")
        if (htmlReportFile.parentFile != null)
            htmlReportFile.parentFile.mkdirs()
        PrintWriter(htmlReportFile).use {
            it.write(htmlString)
        }
        logger.debug("Report created at " + htmlReportFile.absolutePath)
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
