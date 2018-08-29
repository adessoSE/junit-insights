package de.adesso.junitinsights.tools

import com.google.gson.Gson
import de.adesso.junitinsights.model.EventLog
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
    private var testClassLogged = false

    /**
     * Writes a timestamp with some meta information into the buffer.
     * @param timestamp Timestamp for the event
     * @param event Event that triggered the creation of this timestamp
     * @param testClass In case of an event that belongs to a certain test class, this can be included here
     * @param testFunction In case of an event that belongs to a certain test function, this can be included here
     */
    fun writeTimestamp(timestamp: Long, event: String, testClass: String, testFunction: String, testFailing: Boolean = false) {
        if (!testClass.isEmpty()) {
            testClassLogged = true
        }

        val entry = "$timestamp;$event;${trimObjectString(testClass)};${trimObjectString(testFunction)};$testFailing\\n\" +\n\""

        timestamps.append(entry)
        logger.debug("Timestamp saved: $entry")
    }

    /**
     * Creates the html file containing all the collected data.
     */
    fun createReport() {
        if (!testClassLogged)
            return

        val report = ReportCreator.createReport("test", EventLog.events)
        val jsonString = Gson().toJson(report)
        print(jsonString)

        var htmlString = ""
        InsightProperties.templates!!.forEach {
            val path = ClassPathResource(it)
            val fileString = InputStreamReader(path.inputStream, "UTF-8").readText()

            htmlString = when {
                path.filename!!.substringAfterLast(".") == "js" -> "$htmlString \n <script> $fileString </script>"
                path.filename!!.substringAfterLast(".") == "css" -> "$htmlString \n <style> $fileString </style>"
                else -> "$htmlString \n $fileString"
            }
        }

        htmlString = htmlString.replace("\$timestampCsvString", timestamps.toString())

        val htmlReportFile = File(InsightProperties.reportpath + "insight_$currentTime.html")
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
